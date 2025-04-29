package com.example.junggoheaven.domain.payments.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

// TODO: message 수정
public enum PaymentErrorCode implements ErrorCode {
	ORDER_NOT_FOUND("ORDER_NOT_FOUND", HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),

	INVALID_ORDER_AMOUNT("INVALID_ORDER_AMOUNT", HttpStatus.BAD_REQUEST, "주문 금액 불일치"),
	INVALID_ORDER_CUSTOMER_KEY("INVALID_ORDER_CUSTOMER_KEY", HttpStatus.BAD_REQUEST, "주문의 구매자 키가 일치하지 않습니다."),
	INVALID_ORDER_KEY("INVALID_ORDER_KEY", HttpStatus.BAD_REQUEST, "주문 키가 일치하지 않습니다."),

	INVALID_SECRET_KEY("INVALID_SECRET_KEY", HttpStatus.UNAUTHORIZED, "토스 페이먼츠 시크릿 키가 유효하지 않습니다."),
	INVALID_PAYMENT_METHOD("INVALID_PAYMENT_METHOD", HttpStatus.BAD_REQUEST, "결제 수단이 유효하지 않습니다."),
	INVALID_BANK("INVALID_BANK", HttpStatus.BAD_REQUEST, "은행이 유효하지 않습니다.");

	private String code;
	private HttpStatus httpStatus;
	private String message;

	PaymentErrorCode(String code, HttpStatus httpStatus, String message) {
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
