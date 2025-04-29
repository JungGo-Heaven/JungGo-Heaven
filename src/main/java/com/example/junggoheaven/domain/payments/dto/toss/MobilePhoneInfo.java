package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// mobilePhone
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobilePhoneInfo {
	private String customerMobilePhone;
	private String settlementStatus;
	private String receiptUrl;			// 영수증 url

	@Builder
	private MobilePhoneInfo(String customerMobilePhone, String settlementStatus, String receiptUrl) {
		this.customerMobilePhone = customerMobilePhone;
		this.settlementStatus = settlementStatus;
		this.receiptUrl = receiptUrl;
	}

	public  static MobilePhoneInfo of(String customerMobilePhone, String settlementStatus, String receiptUrl) {
		return MobilePhoneInfo.builder()
			.customerMobilePhone(customerMobilePhone).settlementStatus(settlementStatus).receiptUrl(receiptUrl).build();
	}
}
