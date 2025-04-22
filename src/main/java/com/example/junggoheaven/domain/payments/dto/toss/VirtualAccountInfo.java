package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Toss docs 참고 'virtualAccount'
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualAccountInfo {
	private String accountNumber;
	private String bankCode;
	private String customerName;
	private String dueDate;
	private boolean expired;
	private String settlementStatus;
	private String refundStatus;
}
