package com.example.junggoheaven.global.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.repository.UserRepository;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserReader;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserFinder userFinder;
	private final UserReader userReader;

	public SignupResponseDto signup(SignupRequestDto requestDto) {
		String email = requestDto.getEmail();
		String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
		String name = requestDto.getName();
		String phoneNumber = requestDto.getPhoneNumber();
		String address = requestDto.getAddress();


	}
}
