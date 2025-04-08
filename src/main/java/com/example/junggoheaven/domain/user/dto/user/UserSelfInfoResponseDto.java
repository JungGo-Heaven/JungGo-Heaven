package com.example.junggoheaven.domain.user.dto.user;

import java.time.format.DateTimeFormatter;

import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSelfInfoResponseDto {
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	private String createdAt;
	private String modifiedAt;

	@Builder
	private UserSelfInfoResponseDto(String email, String name, String phoneNumber, String address, String createdAt, String modifiedAt) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static UserSelfInfoResponseDto from(User user){
		return UserSelfInfoResponseDto.builder()
			.email(user.getEmail()).name(user.getName()).phoneNumber(user.getPhoneNumber())
			.address(user.getAddress())
			.createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.modifiedAt(user.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.build();
	}
}
