package com.example.junggoheaven.domain.payments.dto.request;

import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.global.common.annotation.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOrderRequestDto {
	@NotNull(message = "구매자를 선택해주세요.")
	private Long buyerId;
	@NotNull(message = "판매 상품을 선택해주세요.")
	private Long productId;
	@NotBlank(message = "판매 상품에 대한 내용을 입력해주세요.")
	private String detail;
	@NotNull(message = "판매 가격을 입력해주세요.")
	private Long amount;
	@NotBlank(message = "결제 수단을 선택해주세요.")
	@ValidEnum(enumClass = PaymentMethod.class, message = "VIRTUAL_ACCOUNT, CARD_PAYMENT 중에서 입력해주세요.")
	private String method;

	@Builder
	private CreateOrderRequestDto(Long buyerId, Long productId, String detail, Long amount, String method) {
		this.buyerId = buyerId;
		this.productId = productId;
		this.detail = detail;
		this.amount = amount;
		this.method = method;
	}

	public static CreateOrderRequestDto of(Long buyerId, Long productId, String detail, Long amount, String method) {
		return CreateOrderRequestDto.builder()
			.buyerId(buyerId).productId(productId).detail(detail).amount(amount).method(method).build();
	}
}
