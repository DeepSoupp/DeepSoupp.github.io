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
public class ProductRequestDTO {
	private Long id;
	private String name, description;
	private int price;
	
	public Product setEntity() {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		return product;
	}
}
