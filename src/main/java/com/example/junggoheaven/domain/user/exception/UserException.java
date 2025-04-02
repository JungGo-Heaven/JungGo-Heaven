package com.example.junggoheaven.domain.user.exception;

import com.example.junggoheaven.global.auth.exception.AuthErrorCode;
import com.example.junggoheaven.global.common.exception.BaseException;

public class UserException extends BaseException {
	public UserException(UserErrorCode errorCode) {
		super(errorCode);
	}
}
