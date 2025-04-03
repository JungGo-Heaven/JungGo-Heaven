package com.example.junggoheaven.global.auth.oauth2;

import java.util.Map;

import com.example.junggoheaven.global.auth.exception.InvalidScopeException;

public class NaverOAuthInfo extends OAuthInfo {

	public NaverOAuthInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getEmail() {
		return (String)getResponse().get("email");
	}

	@Override
	public String getName() {
		return (String)getResponse().get("nickname");
	}

	@Override
	public String getPhoneNumber() {
		return (String)getResponse().get("mobile");
	}

	private Map<String, Object> getResponse() {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");
		if (response == null) {
			throw new InvalidScopeException("response");
		}
		return response;
	}
}
