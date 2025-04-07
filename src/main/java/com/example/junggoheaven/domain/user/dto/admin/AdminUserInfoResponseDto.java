package com.example.junggoheaven.domain.user.dto.admin;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminUserInfoResponseDto {
	private Long userId;
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	private String status;
	private String createdAt;
	private String modifiedAt;
	private String deletedAt;

	@Builder
	private AdminUserInfoResponseDto(Long userId, String email, String name, String phoneNumber, String address,
		String status, String createdAt, String modifiedAt, String deletedAt) {
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.status = status;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.deletedAt = deletedAt;
	}

	public static AdminUserInfoResponseDto from(User user) {
		return AdminUserInfoResponseDto.builder()
			.userId(user.getId()).email(user.getEmail()).name(user.getName())
			.phoneNumber(user.getPhoneNumber()).address(user.getAddress()).status(user.getStatus().name())
			.createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.modifiedAt(user.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.deletedAt(Optional.ofNullable(user.getDeletedAt())
				.map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.orElse(null))
			.build();
	}
}
