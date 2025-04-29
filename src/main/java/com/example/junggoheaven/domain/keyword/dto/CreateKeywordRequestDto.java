package com.example.junggoheaven.domain.keyword.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateKeywordRequestDto {
	@Length(max = 10)
	@NotBlank(message = "키워드 입력은 필수 입니다.")
	private final String keyword;
}
