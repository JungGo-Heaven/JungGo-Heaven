package com.example.junggoheaven.domain.user.enums;

import java.util.Arrays;

import com.example.junggoheaven.domain.user.exception.InvalidUserStatusException;

public enum UserStatus {
	ACTIVE,
	WARNING,
	STOPPED,
	DELETED;

	public static UserStatus of(String status) {
		return Arrays.stream(UserStatus.values())
			.filter(r -> r.name().equalsIgnoreCase(status))
			.findFirst()
			.orElseThrow(InvalidUserStatusException::new);
	}
}
