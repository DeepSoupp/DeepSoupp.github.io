package com.kosta.domain.response;

import com.kosta.domain.FileDTO;
import com.kosta.entity.Favorite;

import lombok.Builder;
import lombok.Data;

// Favorite 엔티티를 클라이언트에 응답할 때 사용하는 DTO
@Data
@Builder
public class FavoriteResponse {
	private Long id;
	private String title, url;
	private FileDTO image;

	public static FavoriteResponse toDTO(Favorite favorite) {
		return FavoriteResponse.builder().id(favorite.getId()).title(favorite.getTitle()).url(favorite.getUrl())
				.image(FileDTO.toDTO(favorite.getImage())).build();
	}
}
