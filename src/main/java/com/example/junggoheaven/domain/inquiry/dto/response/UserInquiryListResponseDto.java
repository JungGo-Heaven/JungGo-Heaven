package com.example.junggoheaven.domain.inquiry.dto.response;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInquiryListResponseDto {
	private Long inquiryId;
	private String title;
	private String inquiryStatus;

	@Builder
	private UserInquiryListResponseDto(Long inquiryId, String title, String inquiryStatus){
		this.inquiryId = inquiryId;
		this.title = title;
		this.inquiryStatus = inquiryStatus;
	}

	public static UserInquiryListResponseDto from(Inquiry inquiry){
		return UserInquiryListResponseDto.builder()
			.inquiryId(inquiry.getId())
			.title(inquiry.getTitle())
			.inquiryStatus(inquiry.getStatus().name())
			.build();
	}
}
