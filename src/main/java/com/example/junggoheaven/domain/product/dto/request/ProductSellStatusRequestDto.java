package com.example.junggoheaven.domain.product.dto.request;


import com.example.junggoheaven.domain.product.enums.SellStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSellStatusRequestDto {

	@NotNull(message = "판매상태를 선택해 주세요")
	private final SellStatus sellStatus;

}
