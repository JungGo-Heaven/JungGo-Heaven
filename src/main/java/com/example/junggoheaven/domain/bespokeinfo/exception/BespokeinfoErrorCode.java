package com.example.junggoheaven.domain.bespokeinfo.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BespokeinfoErrorCode implements ErrorCode {

	NOT_AGREE("NOT_AGREE", HttpStatus.BAD_REQUEST, "사용자 맞춤 정보 사용에 동의하지 않으셨습니다"),
	BESPOKE_AGREE_SAME("BESPOKE_AGREE_SAME", HttpStatus.BAD_REQUEST, "동의 상태가 같습니다");

	private String code;
	private HttpStatus httpStatus;
	private String message;


	BespokeinfoErrorCode (String code, HttpStatus status, String message) {
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
