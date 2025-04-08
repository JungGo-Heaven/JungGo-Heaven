package com.example.junggoheaven.domain.inquiry.exception;

public class AlreadyDeletedInquiryException extends InquiryException {
	public AlreadyDeletedInquiryException() {
		super(InquiryErrorCode.ALREADY_DELETED_INQUIRY);
	}
}
