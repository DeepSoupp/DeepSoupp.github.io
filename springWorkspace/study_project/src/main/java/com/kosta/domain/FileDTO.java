package com.kosta.domain;

import com.kosta.entity.ImageFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FileDTO {
	private String origin, saved, kbSize;
	private String size;
	private Long id;

	public static FileDTO toDTO(ImageFile imageFile) {
		if (imageFile == null)
			return null;
		return FileDTO.builder().id(imageFile.getId()).origin(imageFile.getOriginalName())
				.saved(imageFile.getSavedName()).kbSize(((Double) (imageFile.getFileSize() / 1024.0)).toString())
				.build();

	}
}
