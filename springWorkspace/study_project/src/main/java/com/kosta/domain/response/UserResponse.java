package com.kosta.domain.response;

import com.kosta.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private Long id;
	private String email, name, password;
	
	// 변환된 UserRespinse DTO 객체
	public static UserResponse toDTO(User user) {
		return UserResponse.builder().id(user.getId()).email(user.getEmail()).name(user.getName())
				.password(user.getPassword()).build();
	}
}
