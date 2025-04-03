package com.example.junggoheaven.global.auth.oauth2;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

public class KakaoOAuthInfo extends OAuthInfo {

	public KakaoOAuthInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoAccount {
		private KakaoProfile profile;
		private String email;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoProfile {
		private String nickname;
	}

	@Override
	public String getEmail() {
		return kakaoAccount.getEmail();
	}

	@Override
	public String getName() {
		return kakaoAccount.profile.getNickname();
	}

	@Override
	public String getPhoneNumber() {
		return (String)attributes.get("phone_number");
	}
}
