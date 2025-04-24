package com.example.junggoheaven.domain.keyword.dto;

import java.util.List;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;

import lombok.Getter;

@Getter
public class KeywordsResponseDto {
	private final List<Keyword> keywords;

	private KeywordsResponseDto(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public static KeywordsResponseDto of(KeywordDocument keywordDocument) {
		return new KeywordsResponseDto(Keyword.of(keywordDocument));
	}

	@Getter
	public static class Keyword {
		private final String keyword;
		private final List<String> excludeKeywords;

		private Keyword(String keyword, List<String> excludeKeywords) {
			this.keyword = keyword;
			this.excludeKeywords = excludeKeywords;
		}

		private static List<Keyword> of (KeywordDocument keywordDocument) {
			return keywordDocument.getKeywords().stream()
				.map(k -> new Keyword(k.getKeyword(), k.getExcludeKeywords()))
				.toList();
		}
	}
}
