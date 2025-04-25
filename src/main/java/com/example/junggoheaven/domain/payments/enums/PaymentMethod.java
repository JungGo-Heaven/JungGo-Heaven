package com.example.junggoheaven.domain.payments.enums;

import java.util.Arrays;

import com.example.junggoheaven.domain.payments.exception.InvalidPaymentMethodException;

import lombok.Getter;

@Getter
public enum PaymentMethod {
	VIRTUAL_ACCOUNT("가상계좌"),
	CARD_PAYMENT("카드"),
	EASY_PAYMENT("간편결제"),
	PHONE_PAYMENT("휴대폰");

	private String method;

	PaymentMethod(String method) {
		this.method = method;
	}

	public static PaymentMethod of(String status) {
		return Arrays.stream(PaymentMethod.values())
			.filter(r -> r.name().equalsIgnoreCase(status))
			.findFirst()
			.orElseThrow(InvalidPaymentMethodException::new);
	}
}
