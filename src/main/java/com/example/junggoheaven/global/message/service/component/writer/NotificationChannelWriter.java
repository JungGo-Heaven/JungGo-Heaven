package com.example.junggoheaven.global.message.service.component.writer;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.entity.NotificationChannel;
import com.example.junggoheaven.global.message.repository.NotificationChannelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationChannelWriter {
	private final NotificationChannelRepository notificationChannelRepository;

	public void write(NotificationChannel notificationChannel) {
		notificationChannelRepository.save(notificationChannel);
	}

	public void delete(NotificationChannel notificationChannel) {
		notificationChannelRepository.delete(notificationChannel);
	}
}
