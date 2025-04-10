package com.example.junggoheaven.domain.inquiry.exception;

public class InvalidInquiryStatusException extends InquiryException{
	public InvalidInquiryStatusException() {
		super(InquiryErrorCode.INVALID_INQUIRY_STATUS);
	}
}
