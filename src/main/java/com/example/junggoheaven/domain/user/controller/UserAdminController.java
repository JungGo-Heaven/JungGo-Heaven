package com.example.junggoheaven.domain.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.user.dto.admin.AdminUserInfoResponseDto;
import com.example.junggoheaven.domain.user.dto.admin.AdminUserListResponseDto;
import com.example.junggoheaven.domain.user.dto.admin.AdminUserStatusUpdateRequestDto;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.UserAdminService;
import com.example.junggoheaven.global.common.response.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Secured(UserRole.Authority.ADMIN)
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserAdminController {

	private final UserAdminService userAdminService;

	@PatchMapping("/users/status")
	public ResponseDto<AdminUserInfoResponseDto> updateUserStatus(@Valid @RequestBody AdminUserStatusUpdateRequestDto requestDto) {
		return ResponseDto.success(userAdminService.updateUserStatus(requestDto.getUserId(), requestDto.getStatus()));
	}

	@GetMapping("/users")
	public ResponseDto<Page<AdminUserListResponseDto>> getAdminUserPage(@RequestParam String status,
		@RequestParam String email, @RequestParam int pageNumber, @RequestParam int pageSize) {
		return ResponseDto.success(userAdminService.getAdminUserPage(status, email, pageNumber, pageSize));
	}

	@GetMapping("/users/{userId}")
	public ResponseDto<AdminUserInfoResponseDto> getAdminUserInfo(@PathVariable Long userId) {
		return ResponseDto.success(userAdminService.getAdminUserInfo(userId));
	}
}
