package com.kosta.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Data
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@CreatedDate
	@Column(name = "created_at")
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	LocalDateTime updatedAt;
	
	@Column(name = "login_count")
	private Long loginCount = 0L;
	
	@Column(name = "failed_count")
	private Long failedCount = 0L;
	
	@Column(name = "lastlogin_at")
	LocalDateTime lastLoginAt;
	
	@Column(name = "is_lock")
	private boolean isLock = false;
	
	@Column(name = "lock_count")
	private Long lockCount = 0L;
	

	@Builder
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}

	@Override
	public String getUsername() {
		return "email";
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (isLock && lastLoginAt != null) {
			if (LocalDateTime.now().isAfter(lastLoginAt.plusMinutes(1))) {
				isLock = false;
				lockCount = 0L;
				lastLoginAt = null;
			}
		}
		return !isLock;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
