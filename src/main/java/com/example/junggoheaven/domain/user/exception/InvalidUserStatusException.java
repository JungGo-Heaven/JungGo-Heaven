package com.example.junggoheaven.domain.user.exception;

public class InvalidUserStatusException extends UserException {
	public InvalidUserStatusException() {
		super(UserErrorCode.INVALID_USER_STATUS);
	}
}
