package com.example.junggoheaven.global.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private ErrorCode errorCode;
	private HttpStatus status;

	protected BaseException(ErrorCode errorCode) {
		super(errorCode.getDefaultMessage());
		this.errorCode = errorCode;
		this.status = errorCode.getHttpStatus();
	}

	protected BaseException(ErrorCode errorCode, String message) {
		super(errorCode.getDefaultMessage()+":"+message);
		this.errorCode = errorCode;
		this.status = errorCode.getHttpStatus();
	}
}
