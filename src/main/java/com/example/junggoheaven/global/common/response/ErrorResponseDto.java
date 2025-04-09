package com.example.junggoheaven.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import com.example.junggoheaven.global.common.exception.BaseException;

import jakarta.servlet.http.HttpServletResponse;
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

	// AuthenticationCredentialsNotFoundException 처리를 위해 AuthenticationException 파라미터를 받아서 전달
	public static ErrorResponseDto of(AuthenticationException authException) {
		return ErrorResponseDto.builder()
			.errorClass(authException.getClass().getSimpleName())
			.errorCode(HttpStatus.UNAUTHORIZED.name())
			.message(authException.getMessage())
			.build();
	}

	public static ErrorResponseDto of(String errorClass, String message) {
		return ErrorResponseDto.builder()
			.errorClass(errorClass)
			.message(message)
			.build();
	}
}
