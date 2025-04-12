package com.example.junggoheaven.domain.product.exception;

public class ProductSellStatusSameFlag extends ProductException {

	public ProductSellStatusSameFlag() {
		super(ProductErrorCode.SELL_STATUS_SAME_FLAG);
	}
}
