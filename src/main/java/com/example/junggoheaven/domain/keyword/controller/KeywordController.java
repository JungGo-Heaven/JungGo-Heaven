package com.example.junggoheaven.domain.keyword.controller;

import org.springframework.http.ResponseEntity;
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
import com.example.junggoheaven.domain.keyword.entity.UserDocument;
import com.example.junggoheaven.domain.keyword.service.ESKeywordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KeywordController {
	private final ESKeywordService esKeywordService;

	@GetMapping("/v1/keywords/keyword/{userId}")
	public ResponseEntity<?> findUserKeywords(@PathVariable Long userId) {
		UserDocument userDocument = esKeywordService.findUserKeywords(userId.toString());
		return ResponseEntity.ok(userDocument);
	}

	@PostMapping("/v1/keywords/keyword")
	public ResponseEntity<?> createKeyword(@Valid @RequestBody CreateKeywordRequestDto dto) {
		esKeywordService.addKeyword(dto.getUserId().toString(), dto.getKeyword());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v1/keywords/excludeKeyword")
	public ResponseEntity<?> createExcludeKeyword(@Valid @RequestBody CreateExcludeKeywordRequestDto dto) {
		esKeywordService.addExcludeKeywords(dto.getUserId().toString(), dto.getKeyword(), dto.getExcludeKeyword());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/keywords/keyword")
	public ResponseEntity<?> deleteKeyword(@Valid @RequestBody DeleteKeywordRequestDto dto) {
		esKeywordService.deleteKeyword(dto.getUserId().toString(), dto.getKeyword());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/keywords/excludeKeyword")
	public ResponseEntity<?> deleteExcludeKeyword(@Valid @RequestBody DeleteExcludeKeywordRequestDto dto) {
		esKeywordService.deleteExcludeKeyword(dto.getUserId().toString(), dto.getKeyword(), dto.getExcludeKeyword());
		return ResponseEntity.ok().build();
	}
}
