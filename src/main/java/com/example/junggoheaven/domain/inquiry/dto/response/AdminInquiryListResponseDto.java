package com.example.junggoheaven.domain.inquiry.dto.response;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminInquiryListResponseDto {
	private Long inquiryId;
	private String writerEmail;
	private String title;
	private String inquiryStatus;

	@Builder
	private AdminInquiryListResponseDto(Long inquiryId, String writerEmail, String title, String inquiryStatus) {
		this.inquiryId = inquiryId;
		this.writerEmail = writerEmail;
		this.title = title;
		this.inquiryStatus = inquiryStatus;
	}

	public static AdminInquiryListResponseDto from(Inquiry inquiry) {
		return AdminInquiryListResponseDto.builder()
			.inquiryId(inquiry.getId())
			.writerEmail(inquiry.getWriter().getEmail())
			.title(inquiry.getTitle())
			.inquiryStatus(inquiry.getStatus().name())
			.build();
	}
}
