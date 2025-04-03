package com.example.junggoheaven.global.auth.oauth2;

import java.util.Map;

public class GoogleOAuthInfo extends OAuthInfo {

	public GoogleOAuthInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("given_name");
	}

	@Override
	public String getPhoneNumber() {
		return null;
	}
}
