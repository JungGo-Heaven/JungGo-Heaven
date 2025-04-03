package com.example.junggoheaven.global.auth.oauth2;

import java.util.Map;

public abstract class OAuthInfo {
	protected Map<String, Object> attributes;

	public OAuthInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract String getEmail();

	public abstract String getName();

	public abstract String getPhoneNumber();
}
