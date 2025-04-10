package com.example.junggoheaven.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
	USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 유저는 존재하지 않습니다."),
	EMAIL_NOT_FOUND("EMAIL_NOT_FOUND", HttpStatus.BAD_REQUEST, "존재하지 않는 이메일 입니다."),
	ALREADY_DELETED_USER("ALREADY_DELETED_USER", HttpStatus.BAD_REQUEST, "이미 탈퇴한 사용자 입니다."),
	USER_STATUS_SAME("USER_STATUS_SAME", HttpStatus.BAD_REQUEST, "현재와 동일한 상태 코드 입니다."),
	USER_PASSWORD_SAME("USER_PASSWORD_SAME", HttpStatus.BAD_REQUEST, "현재와 동일한 비밀번호 입니다."),
	INVALID_USER_STATUS("INVALID_USER_STATUS", HttpStatus.BAD_REQUEST, "유효하지 않은 상태 코드 입니다."),
	INVALID_PASSWORD("INVALID_PASSWORD", HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 입니다."),
	INVALID_USER_ROLE("INVALID_USER_ROLE", HttpStatus.BAD_REQUEST, "유효하지 않은 역할 입니다.");

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
