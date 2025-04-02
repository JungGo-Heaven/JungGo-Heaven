package com.example.junggoheaven.global.auth.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", HttpStatus.FORBIDDEN, "접근할 수 있는 권한이 없습니다."),
	ALREADY_EXISTS_EMAIL("ALREADY_EXISTS_EMAIL", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),
	INVALID_EMAIL_PASSWORD("INVALID_EMAIL_PASSWORD", HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 맞지 않습니다.");

	private String code;
	private HttpStatus httpStatus;
	private String message;

	AuthErrorCode(String code, HttpStatus status, String message) {
		this.code = code;
		this.httpStatus = status;
		this.message = message;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getDefaultMessage() {
		return this.message;
	}
}
