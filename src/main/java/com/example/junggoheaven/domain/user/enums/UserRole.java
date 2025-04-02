package com.example.junggoheaven.domain.user.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ROLE_ADMIN(Authority.ADMIN),
	ROLE_USER(Authority.USER),
	ROLE_GUEST(Authority.GUEST);

	private final String userRole;

	// TODO: 예외 발생 추후 수정 예정
	public static UserRole of(String role) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(RuntimeException::new);
	}

	public static class Authority {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String USER = "ROLE_USER";
		public static final String GUEST = "ROLE_GUEST";
	}
}
