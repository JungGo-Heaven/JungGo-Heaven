package com.example.junggoheaven.domain.payments.dto.request;

import com.example.junggoheaven.domain.payments.enums.Banks;
import com.example.junggoheaven.global.common.annotation.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VirtualPaymentRequestDto {
	@NotNull(message = "가상계좌를 생성할 주문ID를 입력해주세요.")
	private Long orderId;
	@ValidEnum(enumClass = Banks.class)
	@NotBlank(message = "가상계좌를 발급할 은행을 선택해주세요.")
	private String bank;
}
