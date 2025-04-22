package com.example.junggoheaven.domain.keyword.service.component;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.UserDocument;
import com.example.junggoheaven.domain.keyword.repository.UserDocumentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeywordWriter {
	private final UserDocumentRepository userDocumentRepository;

	public void write(UserDocument userDocument) {
		userDocumentRepository.save(userDocument);
	}
}
