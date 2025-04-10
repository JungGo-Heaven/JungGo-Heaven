package com.example.junggoheaven.domain.user.exception;

public class AlreadyDeletedUserException extends UserException {
	public AlreadyDeletedUserException(){
		super(UserErrorCode.ALREADY_DELETED_USER);
	}
}
