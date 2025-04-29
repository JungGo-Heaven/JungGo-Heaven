package com.example.junggoheaven.domain.keyword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.keyword.dto.CreateExcludeKeywordRequestDto;
import com.example.junggoheaven.domain.keyword.dto.CreateKeywordRequestDto;
import com.example.junggoheaven.domain.keyword.dto.DeleteExcludeKeywordRequestDto;
import com.example.junggoheaven.domain.keyword.dto.DeleteKeywordRequestDto;
import com.example.junggoheaven.domain.keyword.dto.KeywordsResponseDto;
import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.service.ESKeywordService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KeywordController {
	private final ESKeywordService esKeywordService;

	// test 용
	@GetMapping("/v1/keywords/keyword/{userId}")
	public ResponseEntity<?> findUserKeywordDocument(@PathVariable Long userId) {
		KeywordDocument keywordDocument = esKeywordService.findUserKeywordDocument(userId.toString());
		return ResponseEntity.ok(keywordDocument);
	}

	@GetMapping("/v1/keywords/keyword")
	public ResponseEntity<KeywordsResponseDto> findUserKeywords(@AuthenticationPrincipal AuthUser authUser) {
		return ResponseEntity.ok(esKeywordService.findUserKeywords(authUser.getId().toString()));
	}

	@PostMapping("/v1/keywords/keyword")
	public ResponseEntity<?> createKeyword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody CreateKeywordRequestDto dto
	) {
		esKeywordService.addKeyword(authUser.getId().toString(), dto.getKeyword());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v1/keywords/excludeKeyword")
	public ResponseEntity<?> createExcludeKeyword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody CreateExcludeKeywordRequestDto dto
	) {
		esKeywordService.addExcludeKeywords(authUser.getId().toString(), dto.getKeyword(), dto.getExcludeKeyword());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/keywords/keyword")
	public ResponseEntity<?> deleteKeyword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody DeleteKeywordRequestDto dto
	) {
		esKeywordService.deleteKeyword(authUser.getId().toString(), dto.getKeyword());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/keywords/excludeKeyword")
	public ResponseEntity<?> deleteExcludeKeyword(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody DeleteExcludeKeywordRequestDto dto
	) {
		esKeywordService.deleteExcludeKeyword(authUser.getId().toString(), dto.getKeyword(), dto.getExcludeKeyword());
		return ResponseEntity.ok().build();
	}
}
