package com.example.junggoheaven.domain.keyword.exception;

import org.springframework.http.HttpStatus;

import com.example.junggoheaven.global.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KeywordErrorCode implements ErrorCode {
	KEYWORD_ALREADY_EXISTS("KEYWORD_ALREADY_EXISTS", HttpStatus.NOT_FOUND, "해당 키워드는 이미 등록 되었습니다."),
	KEYWORD_SIZE_OVER("KEYWORD_SIZE_OVER", HttpStatus.BAD_REQUEST, "키워드는 최대 10개 까지 저장 할 수 있습니다."),
	EXCLUDE_KEYWORD_ALREADY_EXISTS("EXCLUDE_KEYWORD_ALREADY_EXISTS", HttpStatus.NOT_FOUND, "해당 제외 키워드는 이미 등록 되었습니다."),
	EXCLUDE_KEYWORD_SIZE_OVER("EXCLUDE_KEYWORD_SIZE_OVER", HttpStatus.BAD_REQUEST, "제외 키워드는 최대 10개 까지 저장 할 수 있습니다.");

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

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
