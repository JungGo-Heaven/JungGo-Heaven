package com.example.junggoheaven.global.common.response;

import com.example.junggoheaven.global.common.exception.BaseException;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto {
	private String errorClass;
	private String errorCode;
	private String message;

	public static ErrorResponseDto of(BaseException errorClass) {
		return ErrorResponseDto.builder()
			.errorClass(errorClass.getClass().getSimpleName())
			.errorCode(errorClass.getErrorCode().getCode())
			.message(errorClass.getMessage())
			.build();
	}

	public static ErrorResponseDto of(String errorClass, String message) {
		return ErrorResponseDto.builder()
			.errorClass(errorClass)
			.message(message)
			.build();
	}
}
