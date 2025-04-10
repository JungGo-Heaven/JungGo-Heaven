package com.example.junggoheaven.domain.product.exception;

public class ProductNotYourException extends ProductException {

	public ProductNotYourException() {
		super(ProductErrorCode.NOT_YOUR_PRODUCT);
	}
}
