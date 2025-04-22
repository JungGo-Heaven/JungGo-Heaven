package com.example.junggoheaven.domain.keyword.service.component;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.repository.KeywordDocumentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeywordFinder {
	private final KeywordDocumentRepository keywordDocumentRepository;

	public KeywordDocument findByUserId(String userId) {
		return keywordDocumentRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
