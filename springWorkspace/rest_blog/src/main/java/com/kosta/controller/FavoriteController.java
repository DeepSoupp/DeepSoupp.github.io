package com.kosta.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.FavoriteRequest;
import com.kosta.domain.response.FavoriteResponse;
import com.kosta.service.FavoriteService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {
	// application.yml 파일의 location 가져오기~
	@Value("${spring.upload.location}")
	private String uploadPath;

	private final FavoriteService favoriteService;

	// 추가하기
	@PostMapping("")
	public ResponseEntity<FavoriteResponse> writeFavorite(FavoriteRequest favorite,
			@RequestParam(name = "image", required = false) MultipartFile file) {
		FavoriteResponse savedFavorite = favoriteService.insertFav(favorite, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
	}

	// 전체 조회 하기
	@GetMapping("")
	public ResponseEntity<List<FavoriteResponse>> getAllFav() {
		List<FavoriteResponse> favoriteList = favoriteService.getAllFav();
		return ResponseEntity.ok(favoriteList);
	}

	// 수정
	@PatchMapping("")
	public ResponseEntity<FavoriteResponse> modifyFavorite(FavoriteRequest favorite,
			@RequestParam(name = "image", required = false) MultipartFile file) {
		FavoriteResponse updatedFavorite = favoriteService.updateFav(favorite, file);
		return ResponseEntity.ok(updatedFavorite);
	}

	// 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<FavoriteResponse> removeFavorite(@PathVariable("id") Long id) {
		FavoriteResponse deletedFavorite = favoriteService.deleteFav(id);
		return ResponseEntity.ok(deletedFavorite);
	}

}
