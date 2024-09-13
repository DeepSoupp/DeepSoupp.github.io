package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.domain.ProductRequestDTO;
import com.kosta.domain.ProductResponseDTO;
import com.kosta.entity.Product;
import com.kosta.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository pr;

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> productList = pr.findAll();
		List<ProductResponseDTO> list = productList.stream().map(p -> ProductResponseDTO.setDTO(p)).toList();
		return list;
	}

	@Override
	public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
		Product product = productRequestDTO.setEntity();
		Product save = pr.save(product);
		return ProductResponseDTO.setDTO(save);
	}

	@Override
	public boolean deleteProduct(Long id) {
		Optional<Product> optProduct = pr.findById(id);
		if (optProduct.isEmpty()) {
			return false;
		}
		pr.deleteById(id);
		return true;
	}

	@Override
	public ProductResponseDTO patchProduct(ProductRequestDTO productRequestDTO) {
		Optional<Product> optProduct = pr.findById(productRequestDTO.getId());
		if (optProduct.isEmpty()) {
			return null;
		}
		Product product = optProduct.get();
		product.setName(productRequestDTO.getName());
		product.setPrice(productRequestDTO.getPrice());
		Product updatedProduct = pr.save(product);
		
		return ProductResponseDTO.setDTO(updatedProduct);

	}
}
