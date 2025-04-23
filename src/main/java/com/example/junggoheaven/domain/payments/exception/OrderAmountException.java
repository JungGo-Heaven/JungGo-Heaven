package com.example.junggoheaven.domain.payments.exception;

public class OrderAmountException extends PaymentException {
	public OrderAmountException(String message) {
		super(PaymentErrorCode.INVALID_ORDER_AMOUNT, message);
	}
}
