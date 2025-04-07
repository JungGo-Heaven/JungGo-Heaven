package com.example.junggoheaven.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.dto.user.GuestAddInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdateInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdatePasswordRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UserSelfInfoResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.exception.InvalidPasswordException;
import com.example.junggoheaven.domain.user.exception.PasswordSameException;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserFinder userFinder;

	@Transactional
	public UserSelfInfoResponseDto addInfo(Long id, GuestAddInfoRequestDto requestDto) {
		User user = userFinder.findValidUserById(id);
		String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
		String phoneNumber = requestDto.getPhoneNumber();
		String address = requestDto.getAddress();

		user.guestAddInfo(password, phoneNumber, address);
		return UserSelfInfoResponseDto.from(user);
	}

	@Transactional
	public UserSelfInfoResponseDto updateUserInfo(Long id, UpdateInfoRequestDto requestDto) {
		User user = userFinder.findValidUserById(id);
		String name = requestDto.getName();
		String phoneNumber = requestDto.getPhoneNumber();
		String address = requestDto.getAddress();

		if (name != null) {
			user.updateName(name);
		}
		if (phoneNumber != null) {
			user.updatePhoneNumber(phoneNumber);
		}
		if (address != null) {
			user.updateAddress(address);
		}

		return UserSelfInfoResponseDto.from(user);
	}

	@Transactional
	public void updateUserPassword(Long id, UpdatePasswordRequestDto requestDto) {
		User user = userFinder.findValidUserById(id);
		String oldPassword = requestDto.getOldPassword();

		if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			throw new InvalidPasswordException();
		}

		String newPassword = bCryptPasswordEncoder.encode(requestDto.getNewPassword());
		if (bCryptPasswordEncoder.matches(oldPassword, newPassword)) {
			throw new PasswordSameException();
		}

		user.updatePassword(newPassword);
	}

	public UserSelfInfoResponseDto getMyInformation(Long id) {
		User user = userFinder.findValidUserById(id);
		return UserSelfInfoResponseDto.from(user);
	}

	@Transactional
	public void deleteUserAccount(Long id) {
		User user = userFinder.findValidUserById(id);
		user.deleteUser();
	}
}
