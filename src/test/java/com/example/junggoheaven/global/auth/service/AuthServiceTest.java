package com.example.junggoheaven.global.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.exception.EmailNotFoundException;
import com.example.junggoheaven.domain.user.service.component.UserChecker;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.reqeust.LoginRequestDto;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;
import com.example.junggoheaven.global.auth.exception.EmailAlreadyExistsException;
import com.example.junggoheaven.global.auth.exception.InvalidEmailPasswordException;
import com.example.junggoheaven.global.auth.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthService authService;

	@Mock
	private JwtUtil jwtUtil;
	@Mock
	private HttpServletResponse servletResponse;
	@Mock
	private UserWriter userWriter;
	@Mock
	private UserFinder userFinder;
	@Mock
	private UserChecker userReader;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	User user;
	String email = "email@email.com";
	String password = "password";
	String encodedPassword;
	String name = "name";
	String phoneNumber = "010-0000-0000";
	String address = "address";

	@BeforeEach
	void setUp() {
		encodedPassword = bCryptPasswordEncoder.encode(password);
		user = User.builder()
			.email(email)
			.password(encodedPassword)
			.name(name)
			.phoneNumber(phoneNumber)
			.address(address)
			.build();
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(user, "modifiedAt", LocalDateTime.now());
	}

	@Test
	public void signup_성공() {
		SignupRequestDto requestDto = new SignupRequestDto(email, password, name, phoneNumber, address);

		given(userReader.existsByUserEmail(anyString())).willReturn(false);
		given(userWriter.saveUser(any())).willReturn(user);

		SignupResponseDto request = authService.signup(requestDto);

		assertThat(request).isNotNull();
		assertThat(request.getName()).isEqualTo(name);
	}

	@Test
	public void signup_실패_중복_이메일() {
		SignupRequestDto requestDto = new SignupRequestDto(email, password, name, phoneNumber, address);

		given(userReader.existsByUserEmail(anyString())).willReturn(true);

		assertThrows(EmailAlreadyExistsException.class, () -> {
			authService.signup(requestDto);
		});
	}

	@Test
	public void login() {
		LoginRequestDto requestDto = new LoginRequestDto(email, password);

		given(userFinder.findByUserEmail(anyString())).willReturn(user);
		given(bCryptPasswordEncoder.matches(password, encodedPassword)).willReturn(true);

		authService.login(requestDto, servletResponse);
	}

	@Test
	public void login_실패_없는_이메일() {
		LoginRequestDto requestDto = new LoginRequestDto(email, password);

		given(userFinder.findByUserEmail(anyString())).willThrow(EmailNotFoundException.class);

		assertThrows(EmailNotFoundException.class, () -> {
			authService.login(requestDto, servletResponse);
		});
	}

	@Test
	public void login_실패_비밀번호_일치_x() {
		LoginRequestDto requestDto = new LoginRequestDto(email, password);

		given(userFinder.findByUserEmail(anyString())).willReturn(user);
		given(bCryptPasswordEncoder.matches(password, encodedPassword)).willReturn(false);

		assertThrows(InvalidEmailPasswordException.class, () -> {
			authService.login(requestDto, servletResponse);
		});
	}
}