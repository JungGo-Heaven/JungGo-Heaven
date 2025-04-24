package com.example.junggoheaven.domain.keyword.service;

import java.util.List;

import org.springframework.stereotype.Service;

import static com.example.junggoheaven.domain.keyword.entity.KeywordDocument.Keyword;

import com.example.junggoheaven.domain.keyword.dto.KeywordsResponseDto;
import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.exception.ExcludeKeywordAlreadyExistsException;
import com.example.junggoheaven.domain.keyword.exception.ExcludeKeywordSizeOverException;
import com.example.junggoheaven.domain.keyword.exception.KeywordAlreadyExistsException;
import com.example.junggoheaven.domain.keyword.exception.KeywordSizeOverException;
import com.example.junggoheaven.domain.keyword.service.component.KeywordFinder;
import com.example.junggoheaven.domain.keyword.service.component.KeywordWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ESKeywordService {
	private final KeywordFinder keywordFinder;
	private final KeywordWriter keywordWriter;

	private static final Long MAX_KEYWORDS_COUNT = 10L;
	private static final Long MAX_EXCLUDE_KEYWORDS_COUNT = 10L;

	public KeywordDocument findUserKeywordDocument(String userId) {
		return keywordFinder.findByUserId(userId);
	}

	public KeywordsResponseDto findUserKeywords(String userId) {
		return KeywordsResponseDto.of(keywordFinder.findByUserId(userId));
	}

	public void addKeyword(String userId, String keyword) {
		KeywordDocument keywordDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = keywordDocument.getKeywords();

		if (keywords.size() >= MAX_KEYWORDS_COUNT) {
			throw new KeywordSizeOverException();
		}

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				throw new KeywordAlreadyExistsException();
			}
		}

		keywordDocument.addKeyword(Keyword.of(keyword));

		keywordWriter.write(keywordDocument);
	}

	public void deleteKeyword(String userId, String keyword) {
		KeywordDocument keywordDocument = keywordFinder.findByUserId(userId);

		keywordDocument.deleteKeyword(keyword);
		keywordWriter.write(keywordDocument);
	}

	public void addExcludeKeywords(String userId, String keyword, String excludeKeyword) {
		KeywordDocument keywordDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = keywordDocument.getKeywords();

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				if (k.getExcludeKeywords().size() >= MAX_EXCLUDE_KEYWORDS_COUNT) {
					throw new ExcludeKeywordSizeOverException();
				}

				if (k.getExcludeKeywords().contains(excludeKeyword)) {
					throw new ExcludeKeywordAlreadyExistsException();
				}

				k.addExcludeKeywords(excludeKeyword);
				keywordWriter.write(keywordDocument);
				return;
			}
		}
	}

	public void deleteExcludeKeyword(String userId, String keyword, String excludeKeyword) {
		KeywordDocument keywordDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = keywordDocument.getKeywords();

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				k.deleteExcludeKeyword(excludeKeyword);
				keywordWriter.write(keywordDocument);
			}
		}

	}
}
