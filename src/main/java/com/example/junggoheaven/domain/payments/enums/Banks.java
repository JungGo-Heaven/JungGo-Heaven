package com.example.junggoheaven.domain.payments.enums;

import java.util.Arrays;

import com.example.junggoheaven.domain.payments.exception.InvalidBankException;

import lombok.Getter;

@Getter
public enum Banks {
	KB("KB국민은행", 06),
	SH("신한은행", 88),
	NH("NH농협은행", 11),
	HANA("하나은행", 81),
	WOORI("우리은행", 20),
	IBK("IBK기업은행", 03);

	private String ko;
	private int code;

	Banks(String ko, int code) {
		this.ko = ko;
		this.code = code;
	}

	public static Banks of(String value) {
		return Arrays.stream(Banks.values())
			.filter(r -> r.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(InvalidBankException::new);
	}
}
