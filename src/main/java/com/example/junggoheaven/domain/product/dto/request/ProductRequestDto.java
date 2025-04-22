package com.example.junggoheaven.domain.product.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequestDto {

	@NotBlank(message = "상품 이름을 입력해 주세요")
	@Size(min = 2, max = 30, message = "상품 이름은 최소 2자에서 최대 30자 사이입니다.")
	private final String name;

	@NotBlank(message = "상품 정보를 입력해 주세요")
	@Size(min = 5, max = 2000, message = "상품 정보는 최소 5자에서 최대 2000자 사이입니다.")
	private final String information;

	@NotNull(message = "가격을 입력해 주세요")
	private final Long price;

	private final Long productImageId;




}
