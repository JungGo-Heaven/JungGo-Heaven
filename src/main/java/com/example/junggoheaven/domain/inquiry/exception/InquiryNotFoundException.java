package com.example.junggoheaven.domain.inquiry.exception;

public class InquiryNotFoundException extends InquiryException{
	public InquiryNotFoundException() {
		super(InquiryErrorCode.INQUIRY_NOT_FOUND);
	}
}
