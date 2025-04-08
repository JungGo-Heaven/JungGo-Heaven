package com.example.junggoheaven.domain.inquiry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RespondInquiryRequestDto {
	@NotBlank(message = "문의에 대한 답안을 작성해주세요.")
	private String response;
}
