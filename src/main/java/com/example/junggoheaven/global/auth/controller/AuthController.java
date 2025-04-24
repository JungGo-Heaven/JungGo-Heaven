package com.example.junggoheaven.global.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.global.auth.dto.reqeust.LoginRequestDto;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;
import com.example.junggoheaven.global.auth.service.AuthService;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/v1/auth/signup")
	public ResponseDto<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto){
		return ResponseDto.success(authService.signup(requestDto));
	}

	@PostMapping("/v1/auth/login")
	public ResponseDto<Void> login(@Valid @RequestBody LoginRequestDto requestDto){
		authService.login(requestDto);
		return ResponseDto.success(null);
	}
}
