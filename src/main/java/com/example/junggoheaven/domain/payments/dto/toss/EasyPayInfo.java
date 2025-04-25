package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EasyPayInfo {
	private String provider;
	private Long amount;
	private Long discountAmount;

	@Builder
	private EasyPayInfo(String provider, Long amount, Long discountAmount) {
		this.provider = provider;
		this.amount = amount;
		this.discountAmount = discountAmount;
	}

	public static EasyPayInfo of(String provider, Long amount, Long discountAmount) {
		return EasyPayInfo.builder()
			.provider(provider).amount(amount).discountAmount(discountAmount).build();
	}
}
