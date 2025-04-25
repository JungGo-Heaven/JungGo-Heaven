package com.example.junggoheaven.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.junggoheaven.global.auth.handler.CustomAuthenticationEntryPoint;
import com.example.junggoheaven.global.auth.handler.OAuth2AuthenticationFailureHandler;
import com.example.junggoheaven.global.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.junggoheaven.global.filter.ExceptionHandlerFilter;
import com.example.junggoheaven.global.filter.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2Service,
		CustomAuthenticationEntryPoint authenticationEntryPoint) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)

			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilterBefore(exceptionHandlerFilter, SecurityContextHolderFilter.class)
			.addFilterBefore(jwtAuthenticationFilter, SecurityContextHolderAwareRequestFilter.class)

			.formLogin(AbstractHttpConfigurer::disable)
			.anonymous(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.rememberMe(AbstractHttpConfigurer::disable)

			.oauth2Login(oauth2 -> oauth2
				.authorizationEndpoint(endpoint -> endpoint
					.authorizationRequestRepository(authorizationRequestRepository())
				)
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oauth2Service))
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler)
			)

			.exceptionHandling(exceptions -> exceptions
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					if (!response.isCommitted()) {
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						response.setContentType("application/json;charset=UTF-8");
						response.getWriter().write("{\"error\":\"접근 거부\",\"message\":\"" + accessDeniedException.getMessage() + "\"}");
					}
				})
			)

			.authorizeHttpRequests(auth -> auth
				.requestMatchers(new AntPathRequestMatcher("/api/*/auth/**"), new AntPathRequestMatcher("/api/v1/location/**"), new AntPathRequestMatcher("/ws/**")).permitAll()
				.requestMatchers("/api/*/payments/virtual/webhook").permitAll()
				.requestMatchers("/test/**").permitAll()
				.anyRequest().authenticated())

			.build();
	}
}
