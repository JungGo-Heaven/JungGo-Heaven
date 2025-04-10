package com.example.junggoheaven.domain.inquiry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateInquiryRequestDto {
	private String title;
	private String body;
}
