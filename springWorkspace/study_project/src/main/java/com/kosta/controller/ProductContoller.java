package com.kosta.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductAddRequest;
import com.kosta.domain.request.ProductDeleteRequest;
import com.kosta.domain.request.ProductEditRequest;
import com.kosta.domain.response.ProductResponse;
import com.kosta.entity.User;
import com.kosta.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductContoller {
	@Value("${spring.upload.location}")
	private String uploadPath;
	private final ProductService productService;

	// 상품추가
	@PostMapping("")
	public ResponseEntity<ProductResponse> addProduct(ProductAddRequest product,
			@RequestParam(name = "image", required = false) MultipartFile file) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(user.getEmail());
		ProductResponse savedProduct = productService.addProdict(product, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}

	// 상품삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Long id,
			@RequestBody ProductDeleteRequest product) {
		ProductResponse deletedProduct = productService.deleteProduct(id, product);
		return ResponseEntity.ok(deletedProduct);
	}

	// 상품수정
	@PatchMapping("")
	public ResponseEntity<ProductResponse> editProduct(ProductEditRequest product,
			@RequestParam(name = "image", required = false) MultipartFile file) {
		ProductResponse updatedProduct = productService.editProduct(product, file);
		return ResponseEntity.ok(updatedProduct);
	}

}
