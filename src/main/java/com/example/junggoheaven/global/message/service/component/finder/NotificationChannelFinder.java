package com.example.junggoheaven.global.message.service.component.finder;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.global.message.entity.NotificationChannel;
import com.example.junggoheaven.global.message.exception.NotificationErrorCode;
import com.example.junggoheaven.global.message.exception.NotificationException;
import com.example.junggoheaven.global.message.repository.NotificationChannelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class NotificationChannelFinder {
	private final NotificationChannelRepository notificationChannelRepository;

	public NotificationChannel findByNotificationChannelId(Long notificationChannelId) {
		return notificationChannelRepository.findById(notificationChannelId).orElseThrow(()-> new NotificationException(
			NotificationErrorCode.NOTIFICATION_NOT_FOUND));
	}

	public List<NotificationChannel> findByUserId(Long userId) {
		return notificationChannelRepository.findByUserId(userId);
	}
}
