package com.example.junggoheaven.domain.inquiry.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminInquiryResponseDto {
	private Long inquiryId;
	private String writerEmail;
	private String inquiryStatus;
	private String title;
	private String body;
	private String createdAt;
	private String modifiedAt;
	private String responderEmail;
	private String response;
	private String responseAt;

	@Builder
	private AdminInquiryResponseDto(Long inquiryId, String writerEmail, String inquiryStatus, String title, String body,
		String createdAt, String modifiedAt, String responderEmail, String response, String responseAt) {
		this.inquiryId = inquiryId;
		this.writerEmail = writerEmail;
		this.inquiryStatus = inquiryStatus;
		this.title = title;
		this.body = body;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.responderEmail = responderEmail;
		this.response = response;
		this.responseAt = responseAt;
	}

	public static AdminInquiryResponseDto from(Inquiry inquiry) {
		return AdminInquiryResponseDto.builder()
			.inquiryId(inquiry.getId())
			.writerEmail(inquiry.getWriter().getEmail())
			.inquiryStatus(inquiry.getStatus().name())
			.title(inquiry.getTitle())
			.body(inquiry.getBody())
			.createdAt(inquiry.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.modifiedAt(inquiry.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.responderEmail(Optional.ofNullable(inquiry.getRespondent()).map(User::getEmail).orElse(null))
			.response(inquiry.getResponse())
			.responseAt(Optional.ofNullable(inquiry.getResponseAt())
				.map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.orElse(null))
			.build();
	}
}
