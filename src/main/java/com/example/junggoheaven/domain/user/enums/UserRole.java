package com.example.junggoheaven.domain.user.enums;

import java.util.Arrays;

import com.example.junggoheaven.domain.user.exception.InvalidUserRoleException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ROLE_ADMIN(Authority.ADMIN),
	ROLE_USER(Authority.USER),
	ROLE_GUEST(Authority.GUEST);

	private final String userRole;

	public static UserRole of(String role) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(InvalidUserRoleException::new);
	}

	public static class Authority {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String USER = "ROLE_USER";
		public static final String GUEST = "ROLE_GUEST";
	}
}
