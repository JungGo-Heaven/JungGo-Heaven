package com.example.junggoheaven.domain.user.dto.admin;

import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.global.common.annotation.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminUserStatusUpdateRequestDto {
	@NotNull(message = "변경하려는 사용자 id를 입력하세요.")
	private Long userId;
	@NotBlank(message = "변경하려는 상태 코드를 입력하세요.")
	@ValidEnum(enumClass = UserStatus.class, message = "ACTIVE, WARNING, STOPPED, DELETED 중 하나를 입력해주세요.")
	private String status;
}