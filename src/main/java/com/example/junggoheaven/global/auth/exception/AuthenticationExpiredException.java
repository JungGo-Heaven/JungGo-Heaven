package com.example.junggoheaven.global.auth.exception;

public class AuthenticationExpiredException extends AuthException{
	public AuthenticationExpiredException() {
		super(AuthErrorCode.EXPIRED_JWT);
	}
}
