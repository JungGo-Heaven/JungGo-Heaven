package com.example.junggoheaven.global.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidResponseDto {
	private String filedName;
	private String message;

	public static ValidResponseDto of(String filedName, String message) {
		return ValidResponseDto.builder().filedName(filedName).message(message).build();
	}
}
