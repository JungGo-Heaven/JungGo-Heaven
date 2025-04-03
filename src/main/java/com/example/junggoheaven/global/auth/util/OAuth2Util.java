package com.example.junggoheaven.global.auth.util;

import java.util.EnumSet;
import java.util.Map;

import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.exception.UnsupportedProviderException;
import com.example.junggoheaven.global.auth.oauth2.GoogleOAuthInfo;
import com.example.junggoheaven.global.auth.oauth2.KakaoOAuthInfo;
import com.example.junggoheaven.global.auth.oauth2.NaverOAuthInfo;
import com.example.junggoheaven.global.auth.oauth2.OAuthInfo;
import com.example.junggoheaven.global.auth.enums.SocialType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Util {

	private OAuthInfo oAuthInfo;
	private UserRole role;

	@Builder
	private OAuth2Util(OAuthInfo oAuthInfo) {
		this.oAuthInfo = oAuthInfo;
		this.role = UserRole.ROLE_GUEST;
	}

	public static OAuth2Util of(SocialType socialType, Map<String, Object> attributes) {

		if (!EnumSet.allOf(SocialType.class).contains(socialType)) {
			throw new UnsupportedProviderException();
		}

		return switch (socialType) {
			case GOOGLE -> ofGoogle(attributes);
			case NAVER -> ofNaver(attributes);
			case KAKAO -> ofKakao(attributes);
		};
	}

	private static OAuth2Util ofGoogle(Map<String, Object> attributes) {
		return OAuth2Util.builder()
			.oAuthInfo(new GoogleOAuthInfo(attributes))
			.build();
	}

	private static OAuth2Util ofNaver(Map<String, Object> attributes) {
		return OAuth2Util.builder()
			.oAuthInfo(new NaverOAuthInfo(attributes))
			.build();
	}

	private static OAuth2Util ofKakao(Map<String, Object> attributes) {
		return OAuth2Util.builder()
			.oAuthInfo(new KakaoOAuthInfo(attributes))
			.build();
	}
}
