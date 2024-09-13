package com.study.domain;

import com.study.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
	private long id;
	private String name, email, password;
	private UserRole role;

	@Builder
	public UserDTO(long id, String name, String email, String password, UserRole role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User setUser() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		return user;
	}
	
	public static UserDTO setUserDTO(User u) {
		return UserDTO.builder()
			.id(u.getId())
			.name(u.getName())
			.email(u.getEmail())
		    .role(u.getRole()) 
			.build();
	}
}
