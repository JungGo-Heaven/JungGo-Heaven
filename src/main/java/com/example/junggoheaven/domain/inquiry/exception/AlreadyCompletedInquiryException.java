package com.example.junggoheaven.domain.inquiry.exception;

public class AlreadyCompletedInquiryException extends InquiryException{
	public AlreadyCompletedInquiryException() {
		super(InquiryErrorCode.ALREADY_COMPLETED_INQUIRY);
	}
}
