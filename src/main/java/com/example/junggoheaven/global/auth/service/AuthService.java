package com.example.junggoheaven.global.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserChecker;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.reqeust.LoginRequestDto;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;
import com.example.junggoheaven.global.auth.exception.EmailAlreadyExistsException;
import com.example.junggoheaven.global.auth.exception.InvalidEmailPasswordException;
import com.example.junggoheaven.global.auth.util.JwtUtil;
import com.example.junggoheaven.global.auth.util.RefreshUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HttpServletResponse response;
	private final JwtUtil jwtUtil;
	private final RefreshUtil refreshUtil;
	private final UserWriter userWriter;
	private final UserChecker userReader;
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

	public void login(LoginRequestDto requestDto) {
		String email = requestDto.getEmail();
		String password = requestDto.getPassword();

		User user = userFinder.findByUserEmail(email);
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			throw new InvalidEmailPasswordException();
		}

		String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getName(), user.getRole());
		jwtUtil.accessSetHeader(accessToken, response);

		String refreshToken = jwtUtil.createRefreshToken(user.getId());
		jwtUtil.refreshSetCookie(refreshToken, response);
		refreshUtil.saveRefreshToken(refreshToken, String.valueOf(user.getId()));
	}

}
