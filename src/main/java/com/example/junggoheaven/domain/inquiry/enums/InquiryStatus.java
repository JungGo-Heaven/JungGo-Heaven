package com.example.junggoheaven.domain.inquiry.enums;

import java.util.Arrays;

import com.example.junggoheaven.domain.inquiry.exception.InvalidInquiryStatusException;

public enum InquiryStatus {
	WAITING,
	COMPLETED,
	DELETED;

	public static InquiryStatus of(String status) {
		return Arrays.stream(InquiryStatus.values())
			.filter(r -> r.name().equalsIgnoreCase(status))
			.findFirst()
			.orElseThrow(InvalidInquiryStatusException::new);
	}
}
