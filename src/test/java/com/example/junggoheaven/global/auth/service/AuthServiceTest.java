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
import com.example.junggoheaven.domain.user.service.component.UserReader;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.reqeust.SignupRequestDto;
import com.example.junggoheaven.global.auth.dto.response.SignupResponseDto;
import com.example.junggoheaven.global.auth.exception.EmailAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthService authService;

	@Mock
	private UserWriter userWriter;
	@Mock
	private UserReader userReader;

	@Spy
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
		user = User.builder().email(email).password(encodedPassword).name(name).phoneNumber(phoneNumber).address(address).build();
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(user, "modifiedAt", LocalDateTime.now());
	}

	@Test
	public void signup_성공() {
		SignupRequestDto requestDto = SignupRequestDto.builder()
			.email(email)
			.password(password)
			.name(name)
			.phoneNumber(phoneNumber)
			.address(address)
			.build();

		given(userReader.existsByUserEmail(anyString())).willReturn(false);
		given(userWriter.saveUser(any())).willReturn(user);

		SignupResponseDto request = authService.signup(requestDto);

		assertThat(request).isNotNull();
		assertThat(request.getName()).isEqualTo(name);
	}

	@Test
	public void signup_실패_중복_이메일(){
		SignupRequestDto requestDto = SignupRequestDto.builder()
			.email(email)
			.password(password)
			.name(name)
			.phoneNumber(phoneNumber)
			.address(address)
			.build();

		given(userReader.existsByUserEmail(anyString())).willReturn(true);

		assertThrows(EmailAlreadyExistsException.class, () -> {
			authService.signup(requestDto);
		});
	}
}