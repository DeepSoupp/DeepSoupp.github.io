package com.kosta.domain;

import com.kosta.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ProductResponseDTO {
	private Long id;
	private String name, description;
	private int price;

	public static ProductResponseDTO setDTO(Product product) {
		return ProductResponseDTO.builder().id(product.getId()).name(product.getName()).description(product.getDescription())
				.price(product.getPrice()).build();
	}
}
