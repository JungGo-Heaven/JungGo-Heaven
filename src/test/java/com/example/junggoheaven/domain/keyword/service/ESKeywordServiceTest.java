package com.example.junggoheaven.domain.keyword.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.keyword.dto.KeywordsResponseDto;
import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.exception.ExcludeKeywordAlreadyExistsException;
import com.example.junggoheaven.domain.keyword.exception.ExcludeKeywordSizeOverException;
import com.example.junggoheaven.domain.keyword.exception.KeywordAlreadyExistsException;
import com.example.junggoheaven.domain.keyword.exception.KeywordSizeOverException;
import com.example.junggoheaven.domain.keyword.service.component.KeywordFinder;
import com.example.junggoheaven.domain.keyword.service.component.KeywordWriter;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class ESKeywordServiceTest {

	@Mock
	private KeywordFinder keywordFinder;

	@Mock
	private KeywordWriter keywordWriter;

	@InjectMocks
	private ESKeywordService esKeywordService;

	User user;
	KeywordDocument keywordDocument;

	@BeforeEach
	void setUp() {
		user = User.of("t1@test.com", "t1", "123-456-7890");

		ReflectionTestUtils.setField(user, "id", 1L);

		keywordDocument = KeywordDocument.of(user);

		KeywordDocument.Keyword keyword = KeywordDocument.Keyword.of("아이폰 3");
		keyword.addExcludeKeywords("케이스");

		keywordDocument.addKeyword(keyword);
	}

	@Test
	void 사용자의_등록_Keywords_조회() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		KeywordsResponseDto keywordsResponseDto = esKeywordService.findUserKeywords(userIdString);

		assertThat(keywordsResponseDto).isNotNull();

		assertThat(keywordsResponseDto.getKeywords()).hasSize(1);
		assertThat(keywordsResponseDto.getKeywords().get(0).getKeyword()).isEqualTo("아이폰 3");

		assertThat(keywordsResponseDto.getKeywords().get(0).getExcludeKeywords()).hasSize(1);
		assertThat(keywordsResponseDto.getKeywords().get(0).getExcludeKeywords().get(0)).isEqualTo("케이스");
	}

	@Test
	void 키워드_추가() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		esKeywordService.addKeyword(userIdString, "겔럭시 s3");
		KeywordsResponseDto keywordsResponseDto = esKeywordService.findUserKeywords(userIdString);

		assertThat(keywordsResponseDto.getKeywords()).hasSize(2);
	}

	@Test
	void 키워드_최대_개수_추가시_예외() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		for (int i = 1; i < 10; i++) {
			esKeywordService.addKeyword(userIdString, "겔럭시 s" + i);
		}

		assertThrows(KeywordSizeOverException.class, () -> esKeywordService.addKeyword(userIdString, "겔럭시 s10"));
	}

	@Test
	void 이미_존재하는_키워드_등록시_예외() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		assertThrows(KeywordAlreadyExistsException.class, () -> esKeywordService.addKeyword(userIdString, "아이폰 3"));
	}

	@Test
	void 키워드_제거() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		esKeywordService.deleteKeyword(userIdString, "아이폰 3");

		assertThat(keywordDocument.getKeywords()).hasSize(0);
	}

	@Test
	void 제외_키워드_추가() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		esKeywordService.addExcludeKeywords(userIdString, "아이폰 3", "충전기");

		assertThat(keywordDocument.getKeywords().get(0).getExcludeKeywords()).hasSize(2);
	}

	@Test
	void 제외_키워드_최대_개수_추가시_예외() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		for (int i = 1; i < 10; i++) {
			esKeywordService.addExcludeKeywords(userIdString, "아이폰 3", "케이스" + i);
		}

		assertThrows(ExcludeKeywordSizeOverException.class, () -> esKeywordService.addExcludeKeywords(userIdString, "아이폰 3", "케이스10"));
	}

	@Test
	void 이미_존재하는_제외_키워드_등록시_예외() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		assertThrows(
			ExcludeKeywordAlreadyExistsException.class, () -> esKeywordService.addExcludeKeywords(userIdString, "아이폰 3", "케이스"));
	}

	@Test
	void 제외_키워드_제거() {
		Long userId = 1L;

		String userIdString = userId.toString();
		given(keywordFinder.findByUserId(userIdString)).willReturn(keywordDocument);

		esKeywordService.deleteExcludeKeyword(userIdString, "아이폰 3", "케이스");

		assertThat(keywordDocument.getKeywords().get(0).getExcludeKeywords()).hasSize(0);
	}
}