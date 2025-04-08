package com.example.junggoheaven.domain.inquiry.exception;

public class InquiryStatusSameException extends InquiryException {
	public InquiryStatusSameException() {
		super(InquiryErrorCode.INQUIRY_STATUS_SAME);
	}
}
