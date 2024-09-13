package com.kosta.domain.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateRequest {
	// 클라이언트로부터 수정할 사용자 정보받음
	private String email, name, password;
}
