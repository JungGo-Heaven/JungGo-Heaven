package com.example.junggoheaven.domain.user.exception;

public class PasswordSameException extends UserException {
	public PasswordSameException() {
		super(UserErrorCode.USER_PASSWORD_SAME);
	}
}
