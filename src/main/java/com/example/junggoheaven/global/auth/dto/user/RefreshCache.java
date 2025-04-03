package com.example.junggoheaven.global.auth.dto.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "refresh_token", timeToLive = 86400)
public class RefreshCache {
	@Id
	private String userId;
	private String token;

	public RefreshCache(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}
}
