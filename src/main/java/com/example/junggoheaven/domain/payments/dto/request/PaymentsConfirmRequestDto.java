package com.example.junggoheaven.domain.payments.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentsConfirmRequestDto {
	@NotNull(message = "가격은 필수 값 입니다.")
	private Long amount;
	@NotBlank(message = "주문Key는 필수 값 입니다.")
	private String orderKey;
	@NotBlank(message = "결제Key는 필수 값 입니다.")
	private String paymentKey;
}
