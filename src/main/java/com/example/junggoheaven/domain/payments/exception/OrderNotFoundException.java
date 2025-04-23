package com.example.junggoheaven.domain.payments.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class OrderNotFoundException extends PaymentException{
	public OrderNotFoundException(){
		super(PaymentErrorCode.ORDER_NOT_FOUND);
	}
}
