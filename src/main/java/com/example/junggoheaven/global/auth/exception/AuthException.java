package com.example.junggoheaven.global.auth.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class AuthException extends BaseException {
	public AuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}
}
