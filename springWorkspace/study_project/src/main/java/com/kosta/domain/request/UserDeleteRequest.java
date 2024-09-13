package com.kosta.domain.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDeleteRequest {
	// 클라이언트로부터 삭제할 사용자 정보받음
	private String email, password;
}
