package com.example.junggoheaven.domain.product.controller;

import com.example.junggoheaven.domain.product.dto.request.ProductRequestDto;
import com.example.junggoheaven.domain.product.dto.response.ProductResponseDto;
import com.example.junggoheaven.domain.product.service.ProductService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		@RequestBody ProductRequestDto productRequestDto
	){
		ProductResponseDto productResponseDto = productService.saveProduct(authUser, productRequestDto);

		return ResponseDto.success(productResponseDto);
	}


	/*
		Product(상품) 다건 조회 메서드
	*/
	@GetMapping("/v1/products")
	public ResponseDto<Page<ProductResponseDto>> findAllProduct(
		@PageableDefault(page = 0, size = 5) Pageable pageable // 기본 page, size 크기 설정 파라미터
	) {
		Page<ProductResponseDto> productResponseDtoPage = productService.findAllProduct(pageable);

		return ResponseDto.success(productResponseDtoPage);
	}

}
