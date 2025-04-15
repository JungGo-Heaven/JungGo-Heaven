package com.example.junggoheaven.domain.user.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.entity.UserKeyword;
import com.example.junggoheaven.domain.user.repository.UserKeywordRepository;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserKeywordService {
	private final UserKeywordRepository userKeywordRepository;
	private final UserFinder userFinder;

	private static final Long MAX_KEYWORDS_COUNT = 10L;

	public void addKeywords(Long userId, List<String> keywords) {
		// User 가 있는지 확인 (삭제 안된 유저)
		User savedUser = userFinder.findNonDeletedUserById(userId);

		// User 가 등록한 Keyword 개수 확인 (최대 10 개)
		if (userKeywordRepository.countByUserId(userId) + keywords.size() > MAX_KEYWORDS_COUNT) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "최대 10개의 키워드만 등록 가능합니다.");
		}

		// Keyword 저장
		List<UserKeyword> userKeywordList = keywords.stream()
			.map((k) -> UserKeyword.of(k, savedUser))
			.toList();

		userKeywordRepository.saveAll(userKeywordList);
	}


}
