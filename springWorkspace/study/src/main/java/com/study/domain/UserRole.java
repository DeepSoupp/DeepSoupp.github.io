package com.study.domain;

public enum UserRole {
	USER("ROLE_USER"),
	LEADER("ROLE_LEADER");
	
	String role;
	
	UserRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
}
