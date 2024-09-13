package com.kosta.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	private int id;
	private String name, email;
	private LocalDateTime createdAt;
}
