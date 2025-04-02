package com.example.junggoheaven.global.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.auth.exception.AuthenticationExpiredException;
import com.example.junggoheaven.global.auth.exception.InvalidJwtSignatureException;
import com.example.junggoheaven.global.auth.exception.InvalidTokenException;
import com.example.junggoheaven.global.auth.exception.TokenNotFoundException;
import com.example.junggoheaven.global.auth.exception.UnsupportedJwtTokenException;
import com.example.junggoheaven.global.auth.util.JwtToken;
import com.example.junggoheaven.global.auth.util.JwtUtil;
import com.example.junggoheaven.global.auth.util.JwtVerificationUtil;
import com.example.junggoheaven.global.auth.util.RefreshUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final JwtVerificationUtil jwtVerificationUtil;
	private final RefreshUtil refreshUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String access = jwtUtil.substringToken(authorizationHeader);

			String refresh = Arrays.stream(request.getCookies())
				.filter(cookie -> cookie.getName().equals("token"))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(TokenNotFoundException::new);

			try {
				Claims claims = jwtUtil.extractClaims(access);

				if (claims == null) {
					throw new InvalidTokenException();
				}

				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					setAuthentication(claims);
				}

				String subject = claims.getSubject();
				String redis = refreshUtil.getRefreshToken(subject);

				// jwt 토큰 만료 검증
				expiredJwtToken(access, refresh, redis, response);

			} catch (SecurityException | MalformedJwtException e) {
				log.error("유효하지 않는 JWT 서명 입니다.", e);
				throw new InvalidJwtSignatureException();
			} catch (UnsupportedJwtException e) {
				log.error("지원되지 않는 JWT 토큰 입니다.", e);
				throw new UnsupportedJwtTokenException();
			} catch (ExpiredJwtException e) {
				log.error("JWT 토큰 시간이 만료 되었습니다.", e);
				throw new AuthenticationExpiredException();
			} catch (Exception e) {
				log.error("Internal server error", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void setAuthentication(Claims claims) {
		Long suerId = Long.valueOf(claims.getSubject());
		String email = claims.get("email", String.class);
		String name = claims.get("name", String.class);
		UserRole userRole = UserRole.of(claims.get("userRole", String.class));

		AuthUser authUser = new AuthUser(suerId, email, userRole, name);
		JwtToken authenticationToken = new JwtToken(authUser);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private void expiredJwtToken(String accessToken, String refreshToken, String redisToken,
		HttpServletResponse response) {
		boolean isAccessTokenExpired = jwtUtil.isTokenExpired(accessToken);
		boolean isRefreshTokenExpired = jwtUtil.isTokenExpired(refreshToken);

		// access token & refresh token 만료
		if (isAccessTokenExpired && isRefreshTokenExpired) {
			log.error("만료된 JWT token 입니다.");
			throw new AuthenticationExpiredException();

			// access token 만료 -> redis에 저장된 토큰과 일치성 확인 후 재발급
		} else if (isAccessTokenExpired) {
			log.info("access token이 만료되었습니다.");
			jwtVerificationUtil.refreshVerifyUsingRedis(refreshToken, redisToken);

			jwtUtil.reissueAccessToken(refreshToken, response);

			// refresh token 만료 -> access 검증 후 refresh 재발급
		} else if (isRefreshTokenExpired) {
			jwtVerificationUtil.accessVerify(accessToken);
			jwtUtil.reissueRefreshToken(accessToken, response);
		}
	}
}
