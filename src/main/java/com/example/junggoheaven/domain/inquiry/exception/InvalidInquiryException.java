package com.example.junggoheaven.domain.inquiry.exception;

public class InvalidInquiryException extends InquiryException{
	public InvalidInquiryException() {
		super(InquiryErrorCode.INVALID_INQUIRY);
	}
}
