package com.example.junggoheaven.domain.user.dto.admin;

import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminUserListResponseDto {
	private Long userId;
	private String email;
	private String status;

	@Builder
	private AdminUserListResponseDto(Long userId, String email, String status) {
		this.userId = userId;
		this.email = email;
		this.status = status;
	}

	public static AdminUserListResponseDto from(User user) {
		return AdminUserListResponseDto.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.status(user.getStatus().name())
			.build();
	}
}
