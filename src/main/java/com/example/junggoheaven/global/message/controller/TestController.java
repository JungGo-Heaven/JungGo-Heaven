package com.example.junggoheaven.global.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.keyword.service.MysqlKeywordService;
import com.example.junggoheaven.global.message.publisher.TestEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
	private final TestEventPublisher testEventPublisher;
	private final MysqlKeywordService mysqlKeywordService;

	@GetMapping("/v1/publish/{name}")
	public ResponseEntity<?> testPublishV1(@PathVariable String name) {
		testEventPublisher.publishProductRegisteredEvent(name);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/v1/publish/OrderStatus/{userId}")
	public ResponseEntity<?> testPublishOrderStatus(@PathVariable Long userId) {
		testEventPublisher.publishOrderStatusChangedEvent(userId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/v1/publish/email/{userId}")
	public ResponseEntity<?> testPublishPushByEmail(@PathVariable Long userId) {
		testEventPublisher.publishPushByEmailEvent(userId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/v2/publish")
	public ResponseEntity<?> testPublishV2(@RequestBody String name) {
		testEventPublisher.publishProductRegisteredEvent(name);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/keywords/{userId}")
	public ResponseEntity<Void> addKeywords(@PathVariable Long userId, @RequestBody List<String> keywords) {
		mysqlKeywordService.addKeywords(userId, keywords);
		return ResponseEntity.ok().build();
	}
}
