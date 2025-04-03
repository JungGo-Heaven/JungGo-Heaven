package com.example.junggoheaven.global.auth.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

public class InvalidScopeException extends OAuth2AuthenticationException {

	private String message;

	public InvalidScopeException(String scope) {
		super(OAuth2ErrorCodes.INVALID_SCOPE);
		this.message = "잘못된 scope: " + scope;
	}
}
