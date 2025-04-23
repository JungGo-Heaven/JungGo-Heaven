package com.example.junggoheaven.domain.payments.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.payments.dto.request.PaymentWebhookRequestDto;
import com.example.junggoheaven.domain.payments.dto.request.VirtualPaymentRequestDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.service.virtual.VirtualAccountService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {

	private final VirtualAccountService virtualAccountService;

	@PostMapping("/payments/virtual")
	public ResponseDto<PaymentApproveResponseDto> createVirtualAccount(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody VirtualPaymentRequestDto requestDto) {
		return ResponseDto.success(virtualAccountService.createVirtualAccount(authUser.getId(),
			requestDto.getOrderId(), requestDto.getBank()));
	}

	@PostMapping("/payments/virtual/webhook")
	public ResponseDto<String> virtualWebhook(@RequestBody PaymentWebhookRequestDto requestDto) {
		return ResponseDto.success(
			virtualAccountService.virtualWebhook(requestDto.getEventType(), requestDto.getOrderId(), requestDto.getStatus(), requestDto.getData()));
	}
}
