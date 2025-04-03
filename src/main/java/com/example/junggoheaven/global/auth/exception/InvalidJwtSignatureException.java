package com.example.junggoheaven.global.auth.exception;

public class InvalidJwtSignatureException extends AuthException{
	public InvalidJwtSignatureException() {
		super(AuthErrorCode.INVALID_JWT_SIGNATURE);
	}
}
