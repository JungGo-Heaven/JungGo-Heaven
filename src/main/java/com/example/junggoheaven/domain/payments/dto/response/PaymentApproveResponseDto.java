package com.example.junggoheaven.domain.payments.dto.response;

import com.example.junggoheaven.domain.payments.dto.toss.VirtualAccountInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentApproveResponseDto {
	private String paymentKey;
	private String orderId;
	private String orderName;
	private String status;
	private String requestedAt;
	private String approvedAt;
	private Long totalAmount;
	private String method;
	private VirtualAccountInfo virtualAccount;

	@Builder
	private PaymentApproveResponseDto(String paymentKey, String orderId, String orderName, String status,
		String requestedAt, String approvedAt, Long totalAmount, String method,
		VirtualAccountInfo virtualAccount) {
		this.paymentKey = paymentKey;
		this.orderId = orderId;
		this.orderName = orderName;
		this.status = status;
		this.requestedAt = requestedAt;
		this.approvedAt = approvedAt;
		this.totalAmount = totalAmount;
		this.method = method;
		this.virtualAccount = virtualAccount;
	}

	public static PaymentApproveResponseDto of(String paymentKey, String orderId, String orderName, String status,
		String requestedAt, String approvedAt, Long totalAmount, String method, VirtualAccountInfo virtualAccount) {
		return PaymentApproveResponseDto.builder()
			.paymentKey(paymentKey)
			.orderId(orderId)
			.orderName(orderName)
			.status(status)
			.requestedAt(requestedAt)
			.approvedAt(approvedAt)
			.totalAmount(totalAmount)
			.method(method)
			.virtualAccount(virtualAccount)
			.build();
	}

	public String getAccountNumber() {
		return virtualAccount != null ? virtualAccount.getAccountNumber() : null;
	}

	public String getDueDate() {
		return virtualAccount != null ? virtualAccount.getDueDate() : null;
	}
}
