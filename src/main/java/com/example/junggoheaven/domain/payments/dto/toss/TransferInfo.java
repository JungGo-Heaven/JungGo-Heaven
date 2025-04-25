package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// transfer
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferInfo {
	private String bankCode;
	private String settlementStatus;

	@Builder
	private TransferInfo(String bankCode, String settlementStatus) {
		this.bankCode = bankCode;
		this.settlementStatus = settlementStatus;
	}

	public static TransferInfo of(String bankCode, String settlementStatus) {
		return TransferInfo.builder()
			.bankCode(bankCode).settlementStatus(settlementStatus).build();
	}
}
