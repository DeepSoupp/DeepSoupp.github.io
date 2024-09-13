package com.kosta.domain.request;

import com.kosta.entity.Favorite;
import com.kosta.entity.ImageFile;

import lombok.Data;

// 클라이언트로부터의 요청을 처리할 때 사용하는 DTO
@Data
public class FavoriteRequest {
	private Long id;
	private String title, url;
	private ImageFile imageFile;

	public Favorite toEntity() {
		return Favorite.builder().id(id).title(title).url(url).image(imageFile).build();
	}
}
