package com.kosta.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.FileDTO;
import com.kosta.entity.ImageFile;
import com.kosta.repository.ImageFileRepository;
import com.kosta.service.imageFileService;
import com.kosta.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class imageFileServiceImpl implements imageFileService {
	private final FileUtils fileUtils;
	private final ImageFileRepository imagefileRepository;

	@Override
	// 이미지 파일을 저장하는 메서드
	public ImageFile saveImage(MultipartFile file) {
		if (file != null) { // 파일이 null 아닌지 확인
			// 파일을 저장하고 저장된 ImageFile 객체를 반환
			ImageFile imageFile = fileUtils.fileUpload(file);

			if (imageFile != null) { // 이미지 파일이 정상적으로 저장되었는지 확인
				ImageFile savedImageFile = imagefileRepository.save(imageFile);
				return savedImageFile;
			}
		}
		return null; // 파일이 없거나 저장되지 않은 경우 null 반환
	}
	@Override
	public FileDTO getImageByImageId(Long id) {
		ImageFile image = imagefileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
		return FileDTO.toDTO(image);
	}
}
