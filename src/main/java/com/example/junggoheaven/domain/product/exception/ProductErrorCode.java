package com.example.junggoheaven.domain.product.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ErrorCode {
	NOT_YOUR_PRODUCT("NOT_YOUR_PRODUCT", HttpStatus.BAD_REQUEST, "회원님의 상품이 아닙니다"),
	NOT_FOUND_PRODUCT("NOT_FOUND_PRODUCT", HttpStatus.NOT_FOUND, "해당 상품은 존재하지 않습니다");


	private String code;
	private HttpStatus httpStatus;
	private String message;

	ProductErrorCode(String code, HttpStatus status, String message) {
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
