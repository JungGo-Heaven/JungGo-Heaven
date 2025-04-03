package com.example.junggoheaven.global.auth.exception;

public class GuestNotAllowedException extends AuthException{
	public GuestNotAllowedException() {
		super(AuthErrorCode.GUEST_NOT_ALLOWED);
	}
}
