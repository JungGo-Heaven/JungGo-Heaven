package com.example.junggoheaven.domain.like.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum LikeErrorCode implements ErrorCode {
	ALREADY_LIKES("ALREADY_LIKES", HttpStatus.BAD_REQUEST, "이미 좋아요 누른 상품 입니다."),
	INVALID_LIKES("INVALID_LIKES", HttpStatus.BAD_REQUEST, "유효하지 않은 좋아요 입니다."),
	LIKE_DELETION("LIKE_DELETION", HttpStatus.FORBIDDEN, "내가 누른 좋아요만 삭제 가능합니다.");

	String code;
	HttpStatus httpStatus;
	String message;

	LikeErrorCode(String code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
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
