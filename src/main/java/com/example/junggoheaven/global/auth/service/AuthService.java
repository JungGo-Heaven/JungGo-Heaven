package com.example.junggoheaven.global.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserRepository;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserReader;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;
import com.example.junggoheaven.global.auth.exception.EmailAlreadyExistsException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserWriter userWriter;
	private final UserReader userReader;
	private final UserFinder userFinder;

	public SignupResponseDto signup(SignupRequestDto requestDto) {
		String email = requestDto.getEmail();
		String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
		String name = requestDto.getName();
		String phoneNumber = requestDto.getPhoneNumber();
		String address = requestDto.getAddress();

		if (userReader.existsByUserEmail(email)) {
			throw new EmailAlreadyExistsException();
		}

		User user = new User(email, password, name, phoneNumber, address);
		User saveUser = userWriter.saveUser(user);

		return SignupResponseDto.from(saveUser);
	}
}
