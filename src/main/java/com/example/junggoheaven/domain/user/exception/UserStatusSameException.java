package com.example.junggoheaven.domain.user.exception;

public class UserStatusSameException extends UserException{
	public UserStatusSameException() {
		super(UserErrorCode.USER_STATUS_SAME);
	}
}
