package com.example.junggoheaven.global.auth.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.common.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.error("Unauthorized error: {}", authException.getMessage());

		if (!response.isCommitted()) {
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			ResponseDto<Object> errorResponse = ResponseDto.fail(HttpStatus.UNAUTHORIZED, authException.getClass().getSimpleName(), authException.getMessage());
			ObjectMapper mapper = new ObjectMapper();
			String jsonResponse = mapper.writeValueAsString(errorResponse);
			response.getWriter().write(jsonResponse);
		}
	}
}
