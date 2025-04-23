package com.example.junggoheaven.domain.payments.dto.toss;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Toss docs 참고 'data'
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Builder
	private WebhookDataInfo(String lastTransactionKey, String paymentKey, String orderId, String orderName,
		String status, String requestedAt, String useEscrow, String secret, Long totalAmount, Long vat, String method,
		VirtualAccountInfo virtualAccount) {
		this.lastTransactionKey = lastTransactionKey;
		this.paymentKey = paymentKey;
		this.orderId = orderId;
		this.orderName = orderName;
		this.status = status;
		this.requestedAt = requestedAt;
		this.useEscrow = useEscrow;
		this.secret = secret;
		this.totalAmount = totalAmount;
		this.vat = vat;
		this.method = method;
		this.virtualAccount = virtualAccount;
	}

	public static WebhookDataInfo of(String lastTransactionKey, String paymentKey, String orderId, String orderName,
		String status, String requestedAt, String useEscrow, String secret, Long totalAmount, Long vat, String method,
		VirtualAccountInfo virtualAccount) {
		return WebhookDataInfo.builder()
			.lastTransactionKey(lastTransactionKey).paymentKey(paymentKey).orderId(orderId).orderName(orderName)
			.status(status).requestedAt(requestedAt).useEscrow(useEscrow).secret(secret).totalAmount(totalAmount)
			.vat(vat).method(method).virtualAccount(virtualAccount).build();
	}
}
