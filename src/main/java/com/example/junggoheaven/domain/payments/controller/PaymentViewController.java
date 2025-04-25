package com.example.junggoheaven.domain.payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.service.virtual.VirtualAccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentViewController {

	private final VirtualAccountService virtualAccountService;

	private final String BASE_URL = "http://localhost:8080/api/v1/payments";

	@GetMapping("/v1/payments/sending")
	public String sending(@RequestParam Long orderId, Model model) {
		model.addAttribute("baseUrl", BASE_URL);

		OrderResponseDto responseDto = virtualAccountService.sending(orderId);
		model.addAttribute("orderId", responseDto.getOrderId());
		model.addAttribute("customerKey", responseDto.getCustomerKey());
		model.addAttribute("amount", responseDto.getAmount());
		model.addAttribute("orderName", responseDto.getOrderName());
		model.addAttribute("method", responseDto.getMethod());
		model.addAttribute("customerEmail", responseDto.getCustomerEmail());
		model.addAttribute("customerName", responseDto.getCustomerName());
		model.addAttribute("customerMobilePhone", responseDto.getCustomerMobilePhone());

		return "sending";
	}

	@GetMapping("/v1/payments/virtual/success")
	public String virtualSuccess(@RequestParam String paymentKey,
		@RequestParam String orderId,
		@RequestParam String amount,
		@RequestParam String paymentType,
		Model model) {

		model.addAttribute("baseUrl", BASE_URL);
		model.addAttribute("paymentKey", paymentKey);
		model.addAttribute("orderId", orderId);
		model.addAttribute("amount", amount);

		return "toss_success";
	}

	@GetMapping("/v1/payments/virtual/fail")
	public String virtualFailWebhook(@RequestParam String message, @RequestParam String status, Model model) {

		model.addAttribute("baseUrl", BASE_URL);
		model.addAttribute("message", message);
		model.addAttribute("status", status);

		return "toss_fail";
	}
}
