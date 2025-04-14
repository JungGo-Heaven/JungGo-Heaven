package com.example.junggoheaven.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.image.entity.ProfileImage;
import com.example.junggoheaven.domain.image.exception.ImageUploadIOException;
import com.example.junggoheaven.domain.image.repository.ProfileImageRepository;
import com.example.junggoheaven.domain.image.service.ProfileImageService;
import com.example.junggoheaven.domain.user.dto.user.GuestAddInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdateInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdatePasswordRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UploadProfileImageRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UserSelfInfoResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.exception.InvalidPasswordException;
import com.example.junggoheaven.domain.user.exception.PasswordSameException;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.util.JwtUtil;
import com.example.junggoheaven.global.auth.util.RefreshUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final ProfileImageRepository profileImageRepository;
	private final HttpServletResponse response;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserFinder userFinder;
	private final JwtUtil jwtUtil;
	private final RefreshUtil refreshUtil;

	@Transactional
	public UserSelfInfoResponseDto addInfo(Long id, GuestAddInfoRequestDto requestDto) {
		User user = userFinder.findValidUserById(id);
		String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
		String phoneNumber = requestDto.getPhoneNumber();
		String address = requestDto.getAddress();

		user.guestAddInfo(password, phoneNumber, address);

		String refreshToken = refreshUtil.getRefreshToken(String.valueOf(user.getId()));
		jwtUtil.reissueAccessToken(refreshToken, response);
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
	
	@Transactional
	public UserSelfInfoResponseDto updateUserImage(Long userId, UploadProfileImageRequestDto requestDto) {
		User user = userFinder.findValidUserById(userId);
		Long imageId = requestDto.getId();

		ProfileImage profileImage = profileImageRepository.findById(imageId).orElseThrow(ImageUploadIOException::new);

		user.updateProfileImage(profileImage);
		return UserSelfInfoResponseDto.from(user);
	}

	public UserSelfInfoResponseDto getMyInformation(Long id) {
		User user = userFinder.findValidUserById(id);
		return UserSelfInfoResponseDto.from(user);
	}

	@Transactional
	public void deleteUserAccount(Long id) {
		User user = userFinder.findByUserId(id);
		refreshUtil.deleteRefreshToken(String.valueOf(id));
		user.deleteUser();
	}
}
