package com.example.junggoheaven.domain.product.exception;

import com.example.junggoheaven.domain.user.exception.UserErrorCode;
import com.example.junggoheaven.global.common.exception.BaseException;

public class ProductException extends BaseException {

	public ProductException(ProductErrorCode errorCode) {
		super(errorCode);
	}
}
