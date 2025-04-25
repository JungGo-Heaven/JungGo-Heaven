package com.example.junggoheaven.domain.payments.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.payments.dto.request.PaymentsConfirmRequestDto;
import com.example.junggoheaven.domain.payments.dto.toss.PaymentWebhookRequestDto;
import com.example.junggoheaven.domain.payments.dto.request.VirtualPaymentRequestDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.service.payment.VirtualAccountService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

	private final VirtualAccountService virtualAccountService;

	@PostMapping("/v1/payments/virtual")
	public ResponseDto<PaymentApproveResponseDto> createVirtualAccount(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody VirtualPaymentRequestDto requestDto) {
		return ResponseDto.success(virtualAccountService.createVirtualAccount(authUser.getId(),
			requestDto.getOrderId(), requestDto.getBank()));
	}

	@PostMapping("/v1/payments/virtual/webhook")
	public ResponseDto<String> virtualWebhook(@RequestBody PaymentWebhookRequestDto requestDto) {
		return ResponseDto.success(
			virtualAccountService.virtualWebhook(requestDto.getEventType(), requestDto.getOrderId(),
				requestDto.getStatus(), requestDto.getData()));
	}

	@PostMapping("/v1/payments/confirm")
	public ResponseDto<PaymentApproveResponseDto> paymentsConfirm(
		@Valid @RequestBody PaymentsConfirmRequestDto requestDto) {
		return ResponseDto.success(
			virtualAccountService.paymentConfirm(requestDto.getAmount(), requestDto.getOrderKey(),
				requestDto.getPaymentKey()));
	}
}
