package com.example.junggoheaven.global.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.message.dto.CreateNotificationRequestDto;
import com.example.junggoheaven.global.message.dto.DeleteNotificationRequestDto;
import com.example.junggoheaven.global.message.service.NotificationChannelService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class NotificationChannelController {
	private final NotificationChannelService notificationChannelService;

	@PostMapping("/v1/notifications")
	public ResponseEntity<?> createNotificationChannel(@AuthenticationPrincipal AuthUser authUser, @RequestBody CreateNotificationRequestDto dto) {
		notificationChannelService.addNotificationChannel(authUser.getId(), dto.getChannelType());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/v1/notifications")
	public ResponseEntity<?> deleteNotificationChannel(@AuthenticationPrincipal AuthUser authUser, @RequestBody DeleteNotificationRequestDto dto) {
		notificationChannelService.deleteNotificationChannel(authUser.getId(), dto.getChannelType());
		return ResponseEntity.ok().build();
	}
}
