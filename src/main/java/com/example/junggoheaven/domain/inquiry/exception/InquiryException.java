package com.example.junggoheaven.domain.inquiry.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class InquiryException extends BaseException {
	public InquiryException(InquiryErrorCode errorCode) {
		super(errorCode);
	}
}
