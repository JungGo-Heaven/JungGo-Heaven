package com.example.junggoheaven.domain.product.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequestDto {

	private final String name;

	private final String information;

	private final Long price;

	private final Long productImageId;




}
