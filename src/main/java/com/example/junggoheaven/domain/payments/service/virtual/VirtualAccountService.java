package com.example.junggoheaven.domain.payments.service.virtual;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentWebhookResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.VirtualAccountInfo;
import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;

public interface VirtualAccountService {
	PaymentApproveResponseDto createVirtualAccount(Long userId, Long orderId, String bank);

	String virtualWebhook(String eventType, String orderId, String status, WebhookDataInfo data);

	OrderResponseDto sending(Long orderId);
}
