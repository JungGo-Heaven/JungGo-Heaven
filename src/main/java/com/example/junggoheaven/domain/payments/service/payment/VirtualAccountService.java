package com.example.junggoheaven.domain.payments.service.payment;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;

public interface VirtualAccountService {
	PaymentApproveResponseDto createVirtualAccount(Long userId, Long orderId, String bank);

	String virtualWebhook(String eventType, String orderId, String status, WebhookDataInfo data);

	OrderResponseDto sending(Long orderId);

	PaymentApproveResponseDto paymentConfirm(Long amount, String orderKey, String paymentKey);
}
