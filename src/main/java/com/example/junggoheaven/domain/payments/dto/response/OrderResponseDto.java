package com.example.junggoheaven.domain.payments.dto.response;

import com.example.junggoheaven.domain.payments.entity.Order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {
	private String orderId;
	private String customerKey;
	private Long amount;
	private String orderName;
	private String method;
	private String customerEmail;
	private String customerName;
	private String customerMobilePhone;

	@Builder
	private OrderResponseDto(String orderId, String customerKey, Long amount, String orderName, String method,
		String customerEmail, String customerName, String customerMobilePhone) {
		this.orderId = orderId;
		this.customerKey = customerKey;
		this.amount = amount;
		this.orderName = orderName;
		this.method = method;
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.customerMobilePhone = customerMobilePhone;
	}

	public static OrderResponseDto from(Order order) {
		String[] split = order.getBuyer().getPhoneNumber().split("-");
		String phoneNumber = String.join("", split);

		return OrderResponseDto.builder()
			.orderId(order.getOrderKey())
			.customerKey(order.getBuyer().getCustomerKey())
			.amount(order.getAmount())
			.orderName(order.getDetails())
			.method(order.getPaymentMethod().getMethod())
			.customerEmail(order.getBuyer().getEmail())
			.customerName(order.getBuyer().getName())
			.customerMobilePhone(phoneNumber)
			.build();

	}
}
