package com.example.junggoheaven.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.user.dto.admin.AdminUserInfoResponseDto;
import com.example.junggoheaven.domain.user.dto.admin.AdminUserListResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.exception.UserStatusSameException;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

@ExtendWith(MockitoExtension.class)
class UserAdminServiceTest {

	@InjectMocks
	UserAdminService userAdminService;
	@Mock
	UserFinder userFinder;

	User user;
	Pageable pageable;

	@BeforeEach
	void setUp() {
		user = User.of("email@email.com", "password", "name", "phoneNumber", "address");
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(user, "modifiedAt", LocalDateTime.now());

		pageable = PageRequest.of(0, 5);
	}

	@Test
	void updateUserStatus() {
		String updateStatus = "STOPPED";
		given(userFinder.findByUserId(any())).willReturn(user);

		AdminUserInfoResponseDto response = userAdminService.updateUserStatus(1L, updateStatus);

		assertThat(response.getStatus()).isEqualTo(user.getStatus().name());
	}

	@Test
	void updateUserStatus_동일한_status_예외() {
		String sameStatus = "ACTIVE";
		given(userFinder.findByUserId(any())).willReturn(user);

		assertThrows(UserStatusSameException.class, () -> userAdminService.updateUserStatus(1L, sameStatus));
	}

	@Test
	void getAdminUserPage() {
		String status = "ACTIVE";
		PageImpl<User> userPage = new PageImpl<>(List.of(user));

		given(userFinder.findUsersForAdmin(any(), any(), any())).willReturn(userPage);

		Page<AdminUserListResponseDto> adminUserPage = userAdminService.getAdminUserPage(status, null, 0, 5);
		AdminUserListResponseDto response = adminUserPage.getContent().get(0);

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(status);
	}

	@Test
	void getAdminUserInfo() {
		given(userFinder.findByUserId(any())).willReturn(user);

		AdminUserInfoResponseDto response = userAdminService.getAdminUserInfo(1L);

		assertThat(response).isNotNull();
		assertThat(response.getUserId()).isEqualTo(user.getId());
	}
}