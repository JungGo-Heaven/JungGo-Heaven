package com.example.junggoheaven.domain.user.dto.admin;

import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.global.common.annotation.ValidEnum;

import lombok.Getter;

@Getter
public class AdminUserStatusUpdateRequestDto {
	private Long userId;
	@ValidEnum(enumClass = UserStatus.class, message = "ACTIVE, WARNING, STOPPED, DELETED 중 하나를 입력해주세요.")
	private String status;
}