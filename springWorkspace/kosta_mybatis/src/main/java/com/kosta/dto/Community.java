package com.kosta.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Community {
	private int id;
	private String title, content;
	private int hit;
	private LocalDateTime createdAt;
	private User creator;
	private List<CommunityFile> fileList;
}
