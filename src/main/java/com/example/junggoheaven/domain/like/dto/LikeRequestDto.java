package com.example.junggoheaven.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDto {
	@NotBlank(message = "좋아요 누를 상품을 선택해주세요.")
	private Long productId;
}
