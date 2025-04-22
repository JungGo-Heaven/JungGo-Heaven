package com.example.junggoheaven.domain.keyword.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateExcludeKeywordsRequestDto {

	@Min(1)
	private Long userId;

	@Length(max = 10)
	@NotBlank(message = "키워드 입력은 필수 입니다.")
	private final String keyword;

	@Size(max = 10)
	private final List<@Length(max = 10) String> excludeKeywords;
}
