package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.domain.request.ProductAddRequest;
import com.kosta.domain.request.ProductDeleteRequest;
import com.kosta.domain.request.ProductEditRequest;
import com.kosta.domain.response.ProductResponse;

public interface ProductService {

	// 상품 등록
	ProductResponse addProdict(ProductAddRequest product, MultipartFile file);

	// 상품 삭제
	ProductResponse deleteProduct(Long id, ProductDeleteRequest product);

	// 상품 수정
	ProductResponse editProduct(ProductEditRequest product, MultipartFile file);

	// 상품 디테일 보기
	ProductResponse getProductById(Long id);

	// 상품 전체 보기
	List<ProductResponse> getAllProduct();
}
