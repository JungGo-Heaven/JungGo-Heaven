package com.example.junggoheaven.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.example.junggoheaven.domain.user.dto.user.GuestAddInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdateInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdatePasswordRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UserSelfInfoResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.domain.user.exception.InvalidPasswordException;
import com.example.junggoheaven.domain.user.exception.PasswordSameException;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.util.JwtUtil;
import com.example.junggoheaven.global.auth.util.RefreshUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private UserFinder userFinder;
	@Mock
	private RefreshUtil refreshUtil;
	@Mock
	private JwtUtil jwtUtil;

	User guestUser;
	User user;

	@BeforeEach
	void setUp() {
		guestUser = new User("email", null, "name", null, null);
		user = new User("email", "password", "name", "010-1234-1234", "address");

		ReflectionTestUtils.setField(guestUser, "id", 1L);
		ReflectionTestUtils.setField(guestUser, "createdAt", LocalDateTime.of(2025, 04, 01, 12, 00));
		ReflectionTestUtils.setField(guestUser, "modifiedAt", guestUser.getCreatedAt());
		ReflectionTestUtils.setField(guestUser, "role", UserRole.ROLE_GUEST);

		ReflectionTestUtils.setField(user, "id", 2L);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.of(2025, 03, 31, 12, 00));
		ReflectionTestUtils.setField(user, "modifiedAt", guestUser.getCreatedAt());
	}

	@Test
	void addInfo() {
		Long userId = 1L;
		String password = "123456";
		GuestAddInfoRequestDto requestDto = new GuestAddInfoRequestDto();

		given(userFinder.findValidUserById(any())).willReturn(guestUser);
		given(bCryptPasswordEncoder.encode(any())).willReturn(password);

		assertThat(guestUser.getRole()).isEqualTo(UserRole.ROLE_GUEST);
		UserSelfInfoResponseDto responseDto = userService.addInfo(userId, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(guestUser.getRole()).isEqualTo(UserRole.ROLE_USER);
	}

	@Test
	void updateUserInfo() {
		String updateName = "updated name";
		String updatePhoneNumber = "updated phone number";
		String updateAddress = "updated address";

		UpdateInfoRequestDto requestDto = new UpdateInfoRequestDto(updateName, updatePhoneNumber, updateAddress);

		given(userFinder.findValidUserById(any())).willReturn(user);

		UserSelfInfoResponseDto responseDto = userService.updateUserInfo(2L, requestDto);
		assertThat(responseDto).isNotNull();
		assertThat(user.getName()).isEqualTo(updateName);
		assertThat(user.getPhoneNumber()).isEqualTo(updatePhoneNumber);
		assertThat(user.getAddress()).isEqualTo(updateAddress);
		assertThat(user.getCreatedAt()).isNotEqualTo(user.getModifiedAt());
	}

	@Test
	void updateUserInfo_null_이면_변화_안_함() {
		String name = user.getName();
		String phoneNumber = user.getPhoneNumber();
		String address = user.getAddress();
		UpdateInfoRequestDto requestDto = new UpdateInfoRequestDto(null, null, null);

		given(userFinder.findValidUserById(any())).willReturn(user);

		UserSelfInfoResponseDto responseDto = userService.updateUserInfo(2L, requestDto);
		assertThat(responseDto).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
		assertThat(user.getAddress()).isEqualTo(address);
		assertThat(user.getCreatedAt()).isNotEqualTo(user.getModifiedAt());
	}

	@Test
	void updateUserPassword() {
		String oldPassword = user.getPassword();
		String newPassword = "newPassword";
		UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto(oldPassword, newPassword);

		given(userFinder.findValidUserById(any())).willReturn(user);
		given(bCryptPasswordEncoder.matches(any(), any())).willReturn(true).willReturn(false);
		given(bCryptPasswordEncoder.encode(any())).willReturn(newPassword);

		userService.updateUserPassword(2L, requestDto);
		assertThat(user.getPassword()).isNotEqualTo(oldPassword);
		assertThat(user.getPassword()).isEqualTo(newPassword);
	}

	@Test
	void updateUserPassword_oldPassword_에러() {
		UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto("oldPassword", "newPassword");
		given(userFinder.findValidUserById(any())).willReturn(user);
		given(bCryptPasswordEncoder.matches(any(), any())).willReturn(false);

		assertThrows(InvalidPasswordException.class, () -> {
			userService.updateUserPassword(2L, requestDto);
		});
	}

	@Test
	void updateUserPassword_newPassword_에러() {
		UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto("oldPassword", "newPassword");
		given(userFinder.findValidUserById(any())).willReturn(user);
		given(bCryptPasswordEncoder.matches(any(), any())).willReturn(true).willReturn(true);

		assertThrows(PasswordSameException.class, () -> {
			userService.updateUserPassword(2L, requestDto);
		});
	}

	@Test
	void getMyInformation() {
		given(userFinder.findValidUserById(any())).willReturn(user);

		UserSelfInfoResponseDto responseDto = userService.getMyInformation(2L);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getName()).isEqualTo(user.getName());
	}

	@Test
	void deleteUserAccount() {
		given(userFinder.findValidUserById(any())).willReturn(user);

		assertThat(user.getDeletedAt()).isNull();

		userService.deleteUserAccount(2L);

		assertThat(user.getStatus()).isEqualTo(UserStatus.DELETED);
		assertThat(user.getDeletedAt()).isNotNull();
	}
}