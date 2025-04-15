package com.example.junggoheaven.global.message.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class NotificationException extends BaseException {
	public NotificationException(NotificationErrorCode errorCode) {
		super(errorCode);
	}
}
