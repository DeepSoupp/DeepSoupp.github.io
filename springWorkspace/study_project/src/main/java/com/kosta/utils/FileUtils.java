package com.kosta.utils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.entity.ImageFile;

@Component
public class FileUtils {

	@Value("${spring.upload.location}")
	private String uploadPath;

	public ImageFile fileUpload(MultipartFile file) {
		try {

			String originalFileName = file.getOriginalFilename();
			Long fileSize = file.getSize();
			String savedFileName = UUID.randomUUID() + "_" + originalFileName;

			InputStream inputStream = file.getInputStream();
			Path path = Paths.get(uploadPath).resolve(savedFileName);
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

			return ImageFile.builder().originalName(originalFileName).savedName(savedFileName).fileSize(fileSize)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
