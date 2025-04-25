package com.example.junggoheaven.domain.payments.exception;

public class InvalidBankException extends PaymentException {
	public InvalidBankException() {
		super(PaymentErrorCode.INVALID_BANK);
	}
}
