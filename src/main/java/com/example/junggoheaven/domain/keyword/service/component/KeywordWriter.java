package com.example.junggoheaven.domain.keyword.service.component;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.repository.KeywordDocumentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeywordWriter {
	private final KeywordDocumentRepository keywordDocumentRepository;

	public void write(KeywordDocument keywordDocument) {
		keywordDocumentRepository.save(keywordDocument);
	}
}
