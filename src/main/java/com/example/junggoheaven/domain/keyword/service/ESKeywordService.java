package com.example.junggoheaven.domain.keyword.service;

import java.util.List;

import org.springframework.stereotype.Service;

import static com.example.junggoheaven.domain.keyword.entity.UserDocument.Keyword;

import com.example.junggoheaven.domain.keyword.entity.UserDocument;
import com.example.junggoheaven.domain.keyword.repository.UserDocumentRepository;
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

	public UserDocument findUserKeywords(String userId) {
		// todo: UserDocument -> ResponseDto
		return keywordFinder.findByUserId(userId);
	}

	public void addKeyword(String userId, String keyword) {
		UserDocument userDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = userDocument.getKeywords();

		if (keywords.size() >= MAX_KEYWORDS_COUNT) {
			// todo: throw error
			return;
		}

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				// todo: throw error
				return;
			}
		}

		Keyword k = Keyword.of(keyword);

		userDocument.addKeyword(k);

		keywordWriter.write(userDocument);
	}

	public void deleteKeyword(String userId, String keyword) {
		UserDocument userDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = userDocument.getKeywords();

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				userDocument.deleteKeyword(k);
				break;
			}
		}

		keywordWriter.write(userDocument);
	}

	public void addExcludeKeywords(String userId, String keyword, List<String> excludeKeywords) {
		UserDocument userDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = userDocument.getKeywords();
		System.out.println("email: " + userDocument.getEmail());
		System.out.println("keywords: " + keywords.size());

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				if (k.getExcludeKeywords().size() + excludeKeywords.size() >= MAX_EXCLUDE_KEYWORDS_COUNT) {
					// todo: throw error
					return;
				}
				k.addExcludeKeywords(excludeKeywords);
				break;
			}
		}

		keywordWriter.write(userDocument);
	}

	public void deleteExcludeKeyword(String userId, String keyword, String excludeKeyword) {
		UserDocument userDocument = keywordFinder.findByUserId(userId);

		List<Keyword> keywords = userDocument.getKeywords();
		boolean found = false;

		for (Keyword k : keywords) {
			if (k.getKeyword().equals(keyword)) {
				for (String ek: k.getExcludeKeywords()) {
					if (ek.equals(excludeKeyword)) {
						k.deleteExcludeKeyword(excludeKeyword);
						found = true;
						break;
					}
				}
			}
			if (found) break;
		}

		keywordWriter.write(userDocument);
	}
}
