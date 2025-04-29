package com.example.junggoheaven.domain.payments.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class PaymentException extends BaseException {
	public PaymentException(PaymentErrorCode code, String message) {
		super(code, message);
	}
	public PaymentException(PaymentErrorCode code) {
		super(code);
	}
}
