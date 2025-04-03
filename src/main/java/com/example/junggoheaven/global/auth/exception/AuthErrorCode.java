package com.example.junggoheaven.global.auth.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", HttpStatus.FORBIDDEN, "접근할 수 있는 권한이 없습니다."),
	ALREADY_EXISTS_EMAIL("ALREADY_EXISTS_EMAIL", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),
	INVALID_EMAIL_PASSWORD("INVALID_EMAIL_PASSWORD", HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 맞지 않습니다."),
	TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", HttpStatus.UNAUTHORIZED, "JWT 토큰을 찾을 수 없습니다."),
	INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다."),
	INVALID_ACCESS_TOKEN("INVALID_ACCESS_TOKEN", HttpStatus.UNAUTHORIZED, "접근 토큰이 잘못 되었습니다."),
	INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", HttpStatus.UNAUTHORIZED, "로그인 토큰이 잘못 되었습니다. 다시 로그인 해주세요."),
	EXPIRED_JWT("EXPIRED_JWT", HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰 입니다."),
	INVALID_JWT_SIGNATURE("INVALID_JWT_SIGNATURE", HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 서명 입니다."),
	UNSUPPORTED_JWT("UNSUPPORTED_JWT", HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰입니다."),
	GUEST_NOT_ALLOWED("GUEST_NOT_ALLOWED", HttpStatus.FOUND, "GUEST 사용자는 이용할 수 없습니다. redirect: /api/v/users/additional-info");

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
