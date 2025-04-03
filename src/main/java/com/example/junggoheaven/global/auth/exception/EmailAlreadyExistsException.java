package com.example.junggoheaven.global.auth.exception;

public class EmailAlreadyExistsException extends AuthException{
	public EmailAlreadyExistsException() {
		super(AuthErrorCode.ALREADY_EXISTS_EMAIL);
	}
}
