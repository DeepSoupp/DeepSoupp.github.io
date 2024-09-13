package com.kosta.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
// 엔티티의 생성 및 수정 시간을 자동으로 감시
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Data
public class Article {
	// 기본키 설정
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false) // UPDATE 시에 ID 컬럼은 제외
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "creator_id", referencedColumnName = "id")
	private User creator;
	
	
	@Builder
	public Article(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
}