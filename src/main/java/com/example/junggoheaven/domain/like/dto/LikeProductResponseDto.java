package com.example.junggoheaven.domain.like.dto;

import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeProductResponseDto {
	private Long productId;
	private String product;

	@Builder
	private LikeProductResponseDto(Long id, String name) {
		this.productId = id;
		this.product = name;
	}

	public static LikeProductResponseDto from(Like like) {
		return LikeProductResponseDto.builder()
			.id(like.getProduct().getId())
			.name(like.getProduct().getName())
			.build();
	}

	public static LikeProductResponseDto from(Product product) {
		return LikeProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.build();
	}
}
