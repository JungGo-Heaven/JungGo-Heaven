package com.example.junggoheaven.global.auth.util;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserReader;
import com.example.junggoheaven.global.auth.exception.InvalidAccessToken;
import com.example.junggoheaven.global.auth.exception.InvalidRefreshToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtVerificationUtil {

	private final JwtUtil jwtUtil;
	private final UserFinder userFinder;
	private final UserReader userReader;

	public void accessVerify(String accessToken) {
		Claims claims = jwtUtil.extractClaims(accessToken);

		String subject = claims.getSubject();
		Long userId = Long.valueOf(subject);
		String email = (String)claims.get("email");

		if (!userFinder.findByUserId(userId).equals(userFinder.FindByUserEmail(email))) {
			log.info("access token이 유효하지 않습니다.");
			throw new InvalidAccessToken();
		}
	}

	public void refreshVerifyUsingRedis(String refreshToken, String redisToken) {
		Claims claims = jwtUtil.extractClaims(refreshToken);
		Long userId = Long.valueOf(claims.getSubject());

		if (!userReader.existsByUserId(userId)) {
			log.info("refresh token이 유효하지 않습니다.");
			throw new InvalidRefreshToken();
		}

		if (redisToken.equals(refreshToken)) {
			log.info("요청받은 refresh token의 cache가 유효하지 않습니다.");
			throw new InvalidRefreshToken();
		}
	}
}
