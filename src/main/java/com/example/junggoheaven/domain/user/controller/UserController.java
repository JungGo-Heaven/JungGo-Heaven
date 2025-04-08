package com.example.junggoheaven.domain.user.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.user.dto.user.GuestAddInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdateInfoRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UpdatePasswordRequestDto;
import com.example.junggoheaven.domain.user.dto.user.UserSelfInfoResponseDto;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.UserService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.auth.util.RefreshUtil;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Secured(UserRole.Authority.GUEST)
	@PatchMapping("/users/additional-info")
	public ResponseDto<UserSelfInfoResponseDto> guestUserAddInfo(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody GuestAddInfoRequestDto requestDto) {
		return ResponseDto.success(userService.addInfo(authUser.getId(), requestDto));
	}

	@PatchMapping("/users/my")
	public ResponseDto<UserSelfInfoResponseDto> updateUserInfo(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody UpdateInfoRequestDto requestDto) {
		return ResponseDto.success(userService.updateUserInfo(authUser.getId(), requestDto));
	}

	@PatchMapping("/users/my/password")
	public ResponseDto<Void> updateUserPassword(@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody UpdatePasswordRequestDto requestDto) {
		userService.updateUserPassword(authUser.getId(), requestDto);
		return ResponseDto.success(null);
	}

	@GetMapping("/users/my")
	public ResponseDto<UserSelfInfoResponseDto> getMyInformation(@AuthenticationPrincipal AuthUser authUser) {
		return ResponseDto.success(userService.getMyInformation(authUser.getId()));
	}

	@DeleteMapping("/users/my")
	public ResponseDto<Void> deleteUserAccount(@AuthenticationPrincipal AuthUser authUser) {
		userService.deleteUserAccount(authUser.getId());
		return ResponseDto.success(null);
	}
}
