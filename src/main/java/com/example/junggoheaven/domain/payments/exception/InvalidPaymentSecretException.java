package com.example.junggoheaven.domain.payments.exception;

public class InvalidPaymentSecretException extends PaymentException {
	public InvalidPaymentSecretException() {
		super(PaymentErrorCode.INVALID_SECRET_KEY);
	}
}
