package com.example.junggoheaven.domain.inquiry.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInquiryResponseDto {
	private String writerEmail;
	private String inquiryStatus;
	private String title;
	private String body;
	private String createdAt;
	private String responderEmail;
	private String response;
	private String responseAt;

	@Builder
	private UserInquiryResponseDto(String writerEmail, String inquiryStatus, String title, String body,
		String createdAt, String responderEmail, String response, String responseAt) {
		this.writerEmail = writerEmail;
		this.inquiryStatus = inquiryStatus;
		this.title = title;
		this.body = body;
		this.createdAt = createdAt;
		this.responderEmail = responderEmail;
		this.response = response;
		this.responseAt = responseAt;
	}

	public static UserInquiryResponseDto from(Inquiry inquiry) {
		return UserInquiryResponseDto.builder()
			.writerEmail(inquiry.getWriter().getEmail())
			.inquiryStatus(inquiry.getStatus().name())
			.title(inquiry.getTitle())
			.body(inquiry.getBody())
			.createdAt(inquiry.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.responderEmail(Optional.ofNullable(inquiry.getRespondent()).map(User::getEmail).orElse(null))
			.response(inquiry.getResponse())
			.responseAt(Optional.ofNullable(inquiry.getResponseAt())
				.map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
				.orElse(null))
			.build();
	}
}
