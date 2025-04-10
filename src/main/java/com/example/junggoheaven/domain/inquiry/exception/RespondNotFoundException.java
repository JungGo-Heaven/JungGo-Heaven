package com.example.junggoheaven.domain.inquiry.exception;

public class RespondNotFoundException extends InquiryException {
	public RespondNotFoundException() {
		super(InquiryErrorCode.RESPOND_NOT_FOUND);
	}
}
