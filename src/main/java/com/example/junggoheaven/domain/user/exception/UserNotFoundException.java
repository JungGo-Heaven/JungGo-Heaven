package com.example.junggoheaven.domain.user.exception;

public class UserNotFoundException extends UserException{
	public UserNotFoundException(){
		super(UserErrorCode.USER_NOT_FOUND);
	}
}
