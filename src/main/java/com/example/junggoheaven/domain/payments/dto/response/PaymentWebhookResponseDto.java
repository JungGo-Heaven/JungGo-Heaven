package com.example.junggoheaven.domain.payments.dto.response;

import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentWebhookResponseDto {
	private String paymentKey;
	private String secret;
	private String status;
	private String bankCode;
	private String accountNumber;
	private String dueDate;
	private OrderInfo order;
	private CustomerInfo customer;

	@Getter
	private static class OrderInfo {
		private Long orderId;
		private String orderKey;
		private String orderStatus;

		@Builder
		private OrderInfo(Long orderId, String orderKey, String orderStatus) {
			this.orderId = orderId;
			this.orderKey = orderKey;
			this.orderStatus = orderStatus;
		}

		public static OrderInfo of(Order order) {
			return OrderInfo.builder()
				.orderId(order.getId())
				.orderKey(order.getOrderKey())
				.orderStatus(order.getStatus().name())
				.build();
		}
	}

	@Getter
	private static class CustomerInfo {
		private Long userId;
		private String customerKey;

		@Builder
		private CustomerInfo(Long userId, String customerKey) {
			this.userId = userId;
			this.customerKey = customerKey;
		}

		public static CustomerInfo of(User user) {
			return CustomerInfo.builder().userId(user.getId()).customerKey(user.getCustomerKey()).build();
		}
	}

	@Builder
	private PaymentWebhookResponseDto(String paymentKey, String bankCode, String accountNumber, String dueDate,
		String secret, String status, Order order, User customer) {
		this.paymentKey = paymentKey;
		this.bankCode = bankCode;
		this.accountNumber = accountNumber;
		this.dueDate = dueDate;
		this.status = status;
		this.secret = secret;
		this.order = OrderInfo.of(order);
		this.customer = CustomerInfo.of(customer);
	}

	public static PaymentWebhookResponseDto of(WebhookDataInfo data, Order order, User customer) {
		return PaymentWebhookResponseDto.builder()
			.paymentKey(data.getPaymentKey())
			.bankCode(data.getVirtualAccount().getBankCode())
			.accountNumber(data.getVirtualAccount().getAccountNumber())
			.dueDate(data.getVirtualAccount().getDueDate())
			.secret(data.getSecret())
			.status(data.getStatus())
			.order(order)
			.customer(customer)
			.build();
	}

}
