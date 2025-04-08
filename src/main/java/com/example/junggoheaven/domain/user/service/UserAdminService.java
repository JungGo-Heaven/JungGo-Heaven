package com.example.junggoheaven.domain.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.dto.admin.AdminUserInfoResponseDto;
import com.example.junggoheaven.domain.user.dto.admin.AdminUserListResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.domain.user.exception.UserStatusSameException;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAdminService {

	private final UserFinder userFinder;

	@Transactional
	public AdminUserInfoResponseDto updateUserStatus(Long userId, String requestStatus) {
		User user = userFinder.findValidUserById(userId);
		UserStatus userStatus = user.getStatus();

		if (requestStatus.equals(userStatus.name())) {
			throw new UserStatusSameException();
		}

		user.updateStatus(UserStatus.of(requestStatus));
		return AdminUserInfoResponseDto.from(user);
	}

	public Page<AdminUserListResponseDto> getAdminUserPage(String status, String email, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return userFinder.findUsersForAdmin(status, email, pageable).map(AdminUserListResponseDto::from);
	}

	public AdminUserInfoResponseDto getAdminUserInfo(Long userId) {
		return AdminUserInfoResponseDto.from(userFinder.findByUserId(userId));
	}
}
