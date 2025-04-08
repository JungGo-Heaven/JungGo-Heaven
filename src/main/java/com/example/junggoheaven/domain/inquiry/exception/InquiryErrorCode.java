package com.example.junggoheaven.domain.inquiry.exception;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

public enum InquiryErrorCode implements ErrorCode {
	INQUIRY_NOT_FOUND("INQUIRY_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 문의를 찾을 수 없습니다."),
	RESPOND_NOT_FOUND("RESPOND_NOT_FOUND", HttpStatus.NOT_FOUND, "작성된 응답을 찾을 수 없습니다. 응답을 먼저 작성해주세요."),

	INVALID_INQUIRY("INVALID_INQUIRY", HttpStatus.BAD_REQUEST, "유효하지 않은 요청 입니다."),
	INVALID_RESPONDENT("INVALID_RESPONDENT", HttpStatus.FORBIDDEN, "응답 작성자가 아닙니다. 응답을 작성한 관리자만 변경 가능합니다."),
	INVALID_INQUIRY_STATUS("INVALID_INQUIRY_STATUS", HttpStatus.BAD_REQUEST, "유효하지 않은 문의 상태 코드 입니다."),

	ALREADY_DELETED_INQUIRY("ALREADY_DELETED_INQUIRY", HttpStatus.BAD_REQUEST, "이미 삭제된 문의 내역 입니다."),
	ALREADY_COMPLETED_INQUIRY("ALREADY_COMPLETED_INQUIRY", HttpStatus.BAD_REQUEST, "이미 응답 완료한 문의 입니다. 응답 변경 시 수정으로 선택해주세요."),

	INQUIRY_STATUS_SAME("INQUIRY_STATUS_SAME", HttpStatus.BAD_REQUEST, "동일한 상태 코드 입니다.");

	private String code;
	private HttpStatus httpStatus;
	private String message;

	InquiryErrorCode(String code, HttpStatus status, String message) {
		this.code = code;
		this.httpStatus = status;
		this.message = message;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getDefaultMessage() {
		return this.message;
	}
}
