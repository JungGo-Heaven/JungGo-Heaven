package com.example.junggoheaven.global.auth.util;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.example.junggoheaven.global.auth.dto.user.AuthUser;

public class JwtToken extends AbstractAuthenticationToken {

	private final AuthUser authUser;

	public JwtToken(AuthUser authUser) {
		super(authUser.getAuthorities());
		this.authUser = authUser;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return authUser;
	}
}
