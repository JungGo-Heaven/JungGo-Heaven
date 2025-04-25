package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentWebhookRequestDto {
	private String createdAt;
	private String eventType;
	private String orderId;
	private String status;
	private String transactionKey;
	private WebhookDataInfo data;
}
