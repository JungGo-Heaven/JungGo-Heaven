package com.example.junggoheaven.global.auth.dto.response;

import java.time.format.DateTimeFormatter;

import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponseDto {
	private Long id;
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	private String createdAt;

	@Builder
	private SignupResponseDto(Long  id, String email, String name, String phoneNumber, String address, String createdAt) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.createdAt = createdAt;
	}

	public static SignupResponseDto from(User user) {
		return SignupResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.phoneNumber(user.getPhoneNumber())
			.address(user.getAddress())
			.createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.build();
	}
}
