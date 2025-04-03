package com.example.junggoheaven.global.auth.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.aop.Handler;
import com.example.junggoheaven.global.auth.dto.user.SocialUser;
import com.example.junggoheaven.global.auth.util.JwtUtil;
import com.example.junggoheaven.global.auth.util.RefreshUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Handler
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;
	private final RefreshUtil refreshUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {
		SocialUser socialUser = (SocialUser)authentication.getPrincipal();

		// 처음 가입하는 회원인 경우, 소셜 정보를 바탕으로 우리 사이트 회원 가입 -> 개인 정보 동의가 있어야 이용 가능
		// redirect -> 정보 입력 페이지로 이동 (상태 코드만 전달하도록 설정)
		if (socialUser.getRole() == UserRole.ROLE_GUEST) {
			log.info("Social Login User: {}는 GUEST 상태 입니다.", socialUser.getEmail());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		Long id = socialUser.getId();
		String email = socialUser.getEmail();
		UserRole role = socialUser.getRole();
		String name = socialUser.getName();

		// JWT 토큰 생성
		String accessToken = jwtUtil.createAccessToken(id, email, name, role);
		String refreshToken = jwtUtil.createRefreshToken(id);

		refreshUtil.saveRefreshToken(refreshToken, email);

		jwtUtil.accessSetHeader(accessToken, response);
		jwtUtil.refreshSetCookie(refreshToken, response);

		response.setStatus(HttpServletResponse.SC_OK);

		Map<String, String> tokenResponse = new HashMap<>();
		tokenResponse.put("accessToken", accessToken);
		tokenResponse.put("refreshToken", refreshToken);

		response.getWriter().write(
			objectMapper.writeValueAsString(tokenResponse)
		);
	}
}
