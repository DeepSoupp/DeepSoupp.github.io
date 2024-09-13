package com.kosta.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.domain.ProductRequestDTO;
import com.kosta.domain.ProductResponseDTO;
import com.kosta.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductService ps;

	@GetMapping("/product")
	@ResponseBody
	public List<ProductResponseDTO> getProducts() {
		List<ProductResponseDTO> productList = ps.getAllProducts();
		return productList;
	}

	@PostMapping("/product")
	@ResponseBody
	public ProductResponseDTO postProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		ProductResponseDTO productList = ps.addProduct(productRequestDTO);
		return productList;
	}

	@DeleteMapping("/product/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
		boolean isDeleted = ps.deleteProduct(id);
		if (isDeleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(500).build();
		}
	}
	@PatchMapping("/product")
	@ResponseBody
	public ResponseEntity<ProductResponseDTO> patchProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		ProductResponseDTO product = ps.patchProduct(productRequestDTO);
		if (product == null) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(product);
	}
}
