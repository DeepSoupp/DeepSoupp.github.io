package com.kosta.domain.response;

import com.kosta.domain.FileDTO;
import com.kosta.entity.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
	private Long id;
	private String name;
	private Integer price;
	private FileDTO image;
	private UserResponse author;

	public static ProductResponse toDto(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName()).price(product.getPrice())
				.image(FileDTO.toDTO(product.getImage())).author(UserResponse.toDTO(product.getAuthor())).build();
	}
}
