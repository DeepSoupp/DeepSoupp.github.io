package com.kosta.service;

import java.util.List;

import com.kosta.domain.ProductRequestDTO;
import com.kosta.domain.ProductResponseDTO;

public interface ProductService {
	List<ProductResponseDTO> getAllProducts();

	ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);

	boolean deleteProduct(Long id);

	ProductResponseDTO patchProduct(ProductRequestDTO productRequestDTO);
}
