package com.example.junggoheaven.domain.product.dto.request;


import com.example.junggoheaven.domain.product.enums.SellStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSellStatusRequestDto {

	private final SellStatus sellStatus;

}
