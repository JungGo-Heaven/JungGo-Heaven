package com.example.junggoheaven.domain.user.exception;

public class InvalidUserRoleException extends UserException {
	public InvalidUserRoleException() {
		super(UserErrorCode.INVALID_USER_ROLE);
	}
}
