package com.example.junggoheaven.global.auth.exception;

public class InvalidRefreshToken extends AuthException {
	public InvalidRefreshToken() {
		super(AuthErrorCode.INVALID_REFRESH_TOKEN);
	}
}
