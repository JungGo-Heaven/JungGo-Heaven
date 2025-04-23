package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Toss docs 참고 'virtualAccount'
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualAccountInfo {
	private String accountNumber;
	private String bankCode;
	private String customerName;
	private String dueDate;
	private boolean expired;
	private String settlementStatus;
	private String refundStatus;

	@Builder
	private VirtualAccountInfo(String accountNumber, String bankCode, String customerName, String dueDate,
		boolean expired, String settlementStatus, String refundStatus) {
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
		this.customerName = customerName;
		this.dueDate = dueDate;
		this.expired = expired;
		this.settlementStatus = settlementStatus;
		this.refundStatus = refundStatus;
	}

	public static VirtualAccountInfo of(String accountNumber, String bankCode, String customerName, String dueDate,
		boolean expired, String settlementStatus, String refundStatus) {
		return VirtualAccountInfo.builder()
			.accountNumber(accountNumber)
			.bankCode(bankCode)
			.customerName(customerName)
			.dueDate(dueDate)
			.expired(expired)
			.settlementStatus(settlementStatus)
			.refundStatus(refundStatus)
			.build();
	}
}
