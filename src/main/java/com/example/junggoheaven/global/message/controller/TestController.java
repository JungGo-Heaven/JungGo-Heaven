package com.example.junggoheaven.global.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.user.service.UserKeywordService;
import com.example.junggoheaven.global.message.event.TestEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	private final TestEventPublisher testEventPublisher;
	private final UserKeywordService userKeywordService;

	@GetMapping("/v1/publish/{name}")
	public ResponseEntity<?> testPublishV1(@PathVariable String name) {
		testEventPublisher.publishProductRegisteredEvent("v1", name);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/v2/publish/{name}")
	public ResponseEntity<?> testPublishV2(@PathVariable String name) {
		testEventPublisher.publishProductRegisteredEvent("v2", name);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/keywords/{userId}")
	public ResponseEntity<Void> addKeywords(@PathVariable Long userId, @RequestBody List<String> keywords) {
		userKeywordService.addKeywords(userId, keywords);
		return ResponseEntity.ok().build();
	}
}
