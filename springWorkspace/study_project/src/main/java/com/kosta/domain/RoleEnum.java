package com.kosta.domain;

// 사용자 역할을 정의하는 열거형
public enum RoleEnum {
	ROLE_USER("ROLE_USER"), // 일반 사용자
	ROLE_ADMIN("ROLE_ADMIN"); // 관리자역할 상품등록 등등

	String role;

	private RoleEnum(String role) {
		this.role = role;
	}
	// 역할을 문자열로 반환
	public String getRole() {
		return role;
	}
}
