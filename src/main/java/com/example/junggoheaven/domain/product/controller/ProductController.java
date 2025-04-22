package com.example.junggoheaven.domain.product.controller;

import com.example.junggoheaven.domain.product.dto.request.ProductRequestDto;
import com.example.junggoheaven.domain.product.dto.request.ProductSellStatusRequestDto;
import com.example.junggoheaven.domain.product.dto.response.ProductResponseDto;
import com.example.junggoheaven.domain.product.service.ProductService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/*
		상품 등록 매서드
	*/
	@PostMapping("/v1/products")
	public ResponseDto<ProductResponseDto> saveProduct(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody ProductRequestDto productRequestDto
	) {
		ProductResponseDto productResponseDto = productService.saveProduct(authUser, productRequestDto);

		return ResponseDto.success(productResponseDto);
	}


	/*
		Product(상품) 다건 조회 메서드
	*/
	@GetMapping("/v1/products")
	public ResponseDto<Page<ProductResponseDto>> findAllProduct(
		//@PageableDefault(sort = , page = 0, size = 5) Pageable pageable // 기본 page, size 크기 설정 파라미터
		// Refactor 고민 요망
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Page<ProductResponseDto> productResponseDtoPage = productService.findAllProduct(page, size);

		return ResponseDto.success(productResponseDtoPage);
	}


	/*
		상품 단건 조회
	*/
	@GetMapping("/v1/products/{productId}")
	public ResponseDto<ProductResponseDto> findProductById(
		@PathVariable("productId") Long productId) {

		ProductResponseDto productResponseDto = productService.findProductById(productId);

		return ResponseDto.success(productResponseDto);
	}


	/*
		상품 삭제 소프트딜리트 메서드
	*/
	@DeleteMapping("/v1/products/{productId}")
	public ResponseDto<ProductResponseDto> softDeleteProduct(
		@PathVariable("productId") Long productId) {

		ProductResponseDto productResponseDto = productService.softDeleteProduct(productId);

		return ResponseDto.success(productResponseDto);
	}


	/*
		상품 정보 수정
	*/
	@PatchMapping("/v1/products/{productId}")
	public ResponseDto<ProductResponseDto> editProduct(
		@AuthenticationPrincipal AuthUser authUser,
		@PathVariable("productId") Long productId,
		@Valid @RequestBody ProductRequestDto productRequestDto
	) {
		ProductResponseDto productResponseDto = productService.editProduct(authUser, productId, productRequestDto);

		return ResponseDto.success(productResponseDto);

	}


	/*
		상품 판매상태 변경
	*/
	@PatchMapping("/v1/products/sellstatus/{productId}")
	public ResponseDto<ProductResponseDto> setSellStatus(
		@AuthenticationPrincipal AuthUser authUser,
		@PathVariable("productId") Long productId,
		@Valid @RequestBody ProductSellStatusRequestDto productSellStatusRequestDto
	) {
		ProductResponseDto productResponseDto = productService.setSellStatus(authUser, productId,
			productSellStatusRequestDto);

		return ResponseDto.success(productResponseDto);
	}


	/*
		상품 끌어올리기 기능
	*/
	@PatchMapping("/v1/products/pull/{productId}")
	public ResponseDto<ProductResponseDto> pullProduct(
		@AuthenticationPrincipal AuthUser authUser,
		@PathVariable("productId") Long productId
	) {
		ProductResponseDto productResponseDto = productService.pullProduct(authUser, productId);

		return ResponseDto.success(productResponseDto);

	}


}
