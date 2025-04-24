package com.example.junggoheaven.domain.payments.service.virtual;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentWebhookResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.VirtualAccountInfo;
import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface VirtualAccountService {
	PaymentApproveResponseDto createVirtualAccount(Long userId, Long orderId, String bank);

	String virtualWebhook(String eventType, String orderId, String status, WebhookDataInfo data);

	OrderResponseDto sending(Long orderId);

	PaymentApproveResponseDto paymentConfirm(Long amount, String orderKey, String paymentKey);
}
