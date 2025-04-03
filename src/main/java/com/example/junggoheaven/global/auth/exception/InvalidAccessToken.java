package com.example.junggoheaven.global.auth.exception;

public class InvalidAccessToken extends AuthException{
	public InvalidAccessToken() {
		super(AuthErrorCode.INVALID_ACCESS_TOKEN);
	}
}
