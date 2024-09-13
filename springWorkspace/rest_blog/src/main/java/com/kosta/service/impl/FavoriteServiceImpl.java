package com.kosta.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.FavoriteRequest;
import com.kosta.domain.response.FavoriteResponse;
import com.kosta.entity.Favorite;
import com.kosta.entity.ImageFile;
import com.kosta.repository.FavoriteRepository;
import com.kosta.service.FavoriteService;
import com.kosta.service.imageFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final imageFileService imageFileService;

	@Override
	public FavoriteResponse insertFav(FavoriteRequest fav, MultipartFile file) {
		ImageFile savedImage = imageFileService.saveImage(file);
		if (savedImage != null)
			fav.setImageFile(savedImage);
		Favorite favorite = fav.toEntity();
		Favorite savedFavorite = favoriteRepository.save(favorite);
		FavoriteResponse result = FavoriteResponse.toDTO(savedFavorite);
		return result;
	}

	@Override
	public List<FavoriteResponse> getAllFav() {
		List<Favorite> favoriteList = favoriteRepository.findAll();
		if (favoriteList.size() > 0) {
			List<FavoriteResponse> favoriteResponseList = favoriteList.stream().map(FavoriteResponse::toDTO).toList();
			return favoriteResponseList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public FavoriteResponse updateFav(FavoriteRequest fav, MultipartFile file) {
		Favorite favorite = favoriteRepository.findById(fav.getId())
				.orElseThrow(() -> new IllegalArgumentException("즐겨찿기 없음"));
		ImageFile savedImage = imageFileService.saveImage(file);
		if (savedImage != null)
			favorite.setImage(savedImage);
		if (fav.getTitle() != null)
			favorite.setTitle(fav.getTitle());
		if (fav.getUrl() != null)
			favorite.setUrl(fav.getUrl());

		Favorite updatedFavorite = favoriteRepository.save(favorite);
		FavoriteResponse result = FavoriteResponse.toDTO(updatedFavorite);
		return result;
	}

	@Override
	public FavoriteResponse deleteFav(Long id) {
		Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("즐겨찿기 없음"));
		favoriteRepository.delete(favorite);
		return FavoriteResponse.toDTO(favorite);
	}

}
