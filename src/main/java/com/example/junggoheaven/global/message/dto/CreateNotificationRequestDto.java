package com.example.junggoheaven.global.message.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateNotificationRequestDto {
	private final String channelType;
}
