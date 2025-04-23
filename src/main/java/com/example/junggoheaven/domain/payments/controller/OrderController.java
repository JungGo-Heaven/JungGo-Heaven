package com.example.junggoheaven.domain.payments.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.payments.dto.request.CreateOrderRequestDto;
import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.service.order.OrderService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/orders")
	public ResponseDto<OrderResponseDto> createOrder(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {
		return ResponseDto.success(orderService.createOrder(authUser.getId(), createOrderRequestDto));
	}
}
