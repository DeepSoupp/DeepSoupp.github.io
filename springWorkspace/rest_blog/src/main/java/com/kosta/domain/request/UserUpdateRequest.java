package com.kosta.domain.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateRequest {
	private String email,name,password;
}
