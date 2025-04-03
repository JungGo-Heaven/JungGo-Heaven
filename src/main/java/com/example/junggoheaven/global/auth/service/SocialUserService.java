package com.example.junggoheaven.global.auth.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.user.SocialUser;
import com.example.junggoheaven.global.auth.enums.SocialType;
import com.example.junggoheaven.global.auth.exception.InvalidOauthInfoException;
import com.example.junggoheaven.global.auth.exception.UnsupportedProviderException;
import com.example.junggoheaven.global.auth.oauth2.OAuthInfo;
import com.example.junggoheaven.global.auth.util.OAuth2Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserWriter userWriter;
	private final UserFinder userFinder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getSocialType(registrationId);
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuth2Util extractAttributes = OAuth2Util.of(socialType, attributes);
		SocialUser socialUser = processOAuthUser(extractAttributes);

		return new SocialUser(
			Collections.singletonList(new SimpleGrantedAuthority(socialUser.getRole().name())), attributes);
	}

	private SocialUser processOAuthUser(OAuth2Util oAuth2Util) {
		OAuthInfo oAuthInfo = oAuth2Util.getOAuthInfo();
		if (oAuthInfo == null) {
			throw new InvalidOauthInfoException();
		}
		return findOrCreateOAuthUser(oAuthInfo);
	}

	private SocialUser findOrCreateOAuthUser(OAuthInfo oAuthInfo) {
		Optional<User> existingUser = userFinder.findByUserEmailOpt(oAuthInfo.getEmail());

		User user = existingUser.orElseGet(() -> {
			User newUser = new User(oAuthInfo.getEmail(), oAuthInfo.getName(), oAuthInfo.getPhoneNumber());
			return userWriter.saveUser(newUser);
		});

		return new SocialUser(user.getId(), user.getEmail(), user.getName(), user.getRole());
	}

	private SocialType getSocialType(String registrationId) {
		return switch (registrationId) {
			case "naver" -> SocialType.NAVER;
			case "Kakao" -> SocialType.KAKAO;
			case "google" -> SocialType.GOOGLE;
			default -> throw new UnsupportedProviderException();
		};
	}
}
