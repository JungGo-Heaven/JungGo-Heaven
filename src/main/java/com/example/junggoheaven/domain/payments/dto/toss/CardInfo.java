package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// card
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInfo {
	private Long amount;
	private String issuerCode;            // 카드 발급사
	private String acquirerCode;        // 카드 매입사
	private String number;
	private int installmentPlanMonths;    // 할부 개월 수
	private String approveNo;
	private String cardType;
	private String acquireStatus;

	@Builder
	private CardInfo(Long amount, String issuerCode, String acquirerCode, String number, int installmentPlanMonths,
		String approveNo, String cardType, String acquireStatus) {
		this.amount = amount;
		this.issuerCode = issuerCode;
		this.acquirerCode = acquirerCode;
		this.number = number;
		this.installmentPlanMonths = installmentPlanMonths;
		this.approveNo = approveNo;
		this.cardType = cardType;
		this.acquireStatus = acquireStatus;
	}

	public static CardInfo of(Long amount, String issuerCode, String acquirerCode, String number, int installmentPlanMonths,
		String approveNo, String cardType, String acquireStatus) {
		return CardInfo.builder()
			.amount(amount).issuerCode(issuerCode).acquirerCode(acquirerCode).number(number).installmentPlanMonths(installmentPlanMonths)
			.approveNo(approveNo).cardType(cardType).acquireStatus(acquireStatus).build();
	}
}
