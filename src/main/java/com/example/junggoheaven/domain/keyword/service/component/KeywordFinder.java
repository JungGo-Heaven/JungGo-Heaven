package com.example.junggoheaven.domain.keyword.service.component;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.UserDocument;
import com.example.junggoheaven.domain.keyword.repository.UserDocumentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeywordFinder {
	private final UserDocumentRepository userDocumentRepository;

	public UserDocument findByUserId(String userId) {
		return userDocumentRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
