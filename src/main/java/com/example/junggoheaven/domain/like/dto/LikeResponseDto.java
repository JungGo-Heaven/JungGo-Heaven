package com.example.junggoheaven.domain.like.dto;

import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponseDto {
	private Long productId;
	private String product;
	private int count;

	@Builder
	private LikeResponseDto(Long productId, String product, int count) {
		this.productId = productId;
		this.product = product;
		this.count = count;
	}

	public static LikeResponseDto from(Like like, int count) {
		return LikeResponseDto.builder()
			.productId(like.getProduct().getId())
			.product(like.getProduct().getName())
			.count(count)
			.build();
	}

	public static LikeResponseDto from(Product product, int count) {
		return LikeResponseDto.builder()
			.productId(product.getId())
			.product(product.getName())
			.count(count)
			.build();
	}
}
