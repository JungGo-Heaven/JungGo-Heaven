package com.example.junggoheaven.domain.inquiry.exception;

public class InvalidRespondentException extends InquiryException {
	public InvalidRespondentException() {
		super(InquiryErrorCode.INVALID_RESPONDENT);
	}
}
