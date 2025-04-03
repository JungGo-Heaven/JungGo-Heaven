package com.example.junggoheaven.global.auth.exception;

public class InvalidTokenException extends AuthException{
	public InvalidTokenException() {
		super(AuthErrorCode.INVALID_TOKEN);
	}
}
