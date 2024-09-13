package com.kosta.dto;

import lombok.Data;

@Data
public class CommunityFile {
	private int id, communityId;
	private String originFileName, storedFilePath;
	private long fileSize;
}
