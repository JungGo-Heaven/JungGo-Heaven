package com.example.junggoheaven.global.auth.exception;

public class InvalidEmailPasswordException extends AuthException {
	public InvalidEmailPasswordException() {
		super(AuthErrorCode.INVALID_EMAIL_PASSWORD);
	}
}
