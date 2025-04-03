package com.example.junggoheaven.global.auth.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserChecker;
import com.example.junggoheaven.global.auth.exception.TokenNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final RefreshUtil refreshUtil;
	private final UserFinder userFinder;

	private static final String BEARER_PREFIX = "Bearer ";
	private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분
	private static final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 1000L; // 1일
	private final UserChecker userReader;

	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String createAccessToken(Long userId, String email, String name, UserRole userRole) {
		Date date = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(String.valueOf(userId))
				.claim("email", email)
				.claim("name", name)
				.claim("userRole", userRole)
				.setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
				.setIssuedAt(date)
				.signWith(key, signatureAlgorithm)
				.compact();
	}

	public String createRefreshToken(Long userId) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
			.setIssuedAt(date)
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	public String substringToken(String tokenValue) {
		if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
			return tokenValue.substring(7);
		}
		throw new TokenNotFoundException();
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	// token의 만료 여부 확인 -> 유효한 경우에만 false
	public boolean isTokenExpired(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

			// 현재 시간과 토큰의 만료 시간 비교
			Date expirationDate = claims.getExpiration();
			return expirationDate != null && expirationDate.before(new Date());
		} catch (Exception e) {
			// 토큰 파싱 중 발생하는 모든 예외(만료, 서명 오류 등)를 true로 처리
			return true;
		}
	}

	// access token 재발급
	public void reissueAccessToken(String refreshToken, HttpServletResponse response) {
		Claims claims = extractClaims(refreshToken);
		Long userId = Long.valueOf(claims.getSubject());

		User authUser = userFinder.findByUserId(userId);
		String newAccessToken = createAccessToken(authUser.getId(), authUser.getEmail(), authUser.getName(),
			authUser.getRole());
		accessSetHeader(newAccessToken, response);

		String newRefreshToken = createRefreshToken(authUser.getId());
		refreshSetCookie(newRefreshToken, response);
	}

	// refresh token 재발급
	public void reissueRefreshToken(String accessToken, HttpServletResponse response) {
		String userId = extractClaims(accessToken).getSubject();

		String newRefreshToken = createRefreshToken(Long.valueOf(userId));
		refreshUtil.reissueRefreshToken(newRefreshToken, userId);
		refreshSetCookie(newRefreshToken, response);
	}

	public void accessSetHeader(String accessToken, HttpServletResponse response) {
		response.setHeader("Authorization", accessToken);
	}

	public void refreshSetCookie(String refreshToken, HttpServletResponse response) {
		Cookie cookie = new Cookie("token", refreshToken);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
