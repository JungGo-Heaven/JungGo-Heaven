package com.example.junggoheaven.global.auth.util;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.auth.dto.user.RefreshCache;
import com.example.junggoheaven.global.auth.exception.AuthenticationExpiredException;
import com.example.junggoheaven.global.auth.repository.RefreshRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshUtil {

	private final RefreshRepository refreshRepository;

	@Cacheable(value = "refresh", key = "#userId", cacheManager = "redisCache")
	public String saveRefreshToken(String token, String userId) {
		return token;
	}

	@Cacheable(value = "refresh", key = "#userId", cacheManager = "redisCache")
	public String getRefreshToken(String userId) {
		RefreshCache refreshToken = refreshRepository.findById(userId)
			.orElseThrow(AuthenticationExpiredException::new);
		return refreshToken.getToken();
	}

	@CacheEvict(value = "refresh", key = "#userId", cacheManager = "redisCache")
	public void deleteRefreshToken(String userId) {
		refreshRepository.deleteById(userId);
	}

	@CachePut(value = "refresh", key = "#userId", cacheManager = "redisCache")
	public String reissueRefreshToken(String token, String userId) {
		deleteRefreshToken(userId);
		return token;
	}
}
