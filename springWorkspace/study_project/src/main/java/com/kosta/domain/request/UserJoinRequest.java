package com.kosta.domain.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinRequest {
	// 클라이언트로부터 사용자 가입 정보를 수집
	private String email, name, password;
}
