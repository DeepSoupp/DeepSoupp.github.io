package com.kosta.domain.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignUpRequest {
	private String email,name,password;
}
