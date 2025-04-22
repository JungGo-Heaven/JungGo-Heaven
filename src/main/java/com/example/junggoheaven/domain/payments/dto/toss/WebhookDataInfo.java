package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Toss docs 참고 'data'
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WebhookDataInfo {
	private String lastTransactionKey;
	private String paymentKey;
	private String orderId;
	private String orderName;
	private String status;
	private String requestedAt;
	private String useEscrow;
	private String secret;
	private Long totalAmount;
	private Long vat;
	private String method;
	private VirtualAccountInfo virtualAccount;
}
