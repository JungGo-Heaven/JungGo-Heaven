package com.example.junggoheaven.domain.payments.exception;

public class InvalidPaymentMethodException extends PaymentException {
	public InvalidPaymentMethodException() {
		super(PaymentErrorCode.INVALID_PAYMENT_METHOD);
	}
}
