package com.example.junggoheaven.global.message.dto;

import com.example.junggoheaven.global.common.annotation.ValidEnum;
import com.example.junggoheaven.global.message.enums.ChannelType;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateNotificationRequestDto {
	@NotBlank(message = "등록하려는 채널을 입력하세요.")
	@ValidEnum(enumClass = ChannelType.class, message = "알림 채널이 존재하지 않습니다.")
	private final String channelType;

	private final String token;
}
