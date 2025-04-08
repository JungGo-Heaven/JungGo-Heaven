package com.example.junggoheaven.domain.inquiry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateInquiryRequestDto {
	@NotBlank(message = "제목을 작성해주세요.")
	private String title;
	@NotBlank(message = "문의 내용을 작성해주세요.")
	private String body;
}
