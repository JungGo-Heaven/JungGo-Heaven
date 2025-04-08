package com.example.junggoheaven.domain.inquiry.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum InquiryErrorCode implements ErrorCode {
	INQUIRY_NOT_FOUND("INQUIRY_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 문의를 찾을 수 없습니다."),
	INVALID_INQUIRY("INVALID_INQUIRY", HttpStatus.BAD_REQUEST, "유효하지 않은 요청 입니다.");

	private String code;
	private HttpStatus httpStatus;
	private String message;

	InquiryErrorCode(String code, HttpStatus status, String message) {
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
