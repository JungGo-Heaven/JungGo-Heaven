package com.example.junggoheaven.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
	EMAIL_NOT_FOUND("EMAIL_NOT_FOUND", HttpStatus.BAD_REQUEST, "존재하지 않는 이메일 입니다.");

	private String code;
	private HttpStatus httpStatus;
	private String message;

	UserErrorCode(String code, HttpStatus status, String message) {
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
