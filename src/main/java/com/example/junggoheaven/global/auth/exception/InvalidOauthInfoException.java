package com.example.junggoheaven.global.auth.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

public class InvalidOauthInfoException extends OAuth2AuthenticationException {
	public InvalidOauthInfoException() {
		super(OAuth2ErrorCodes.INVALID_CLIENT);
	}
}
