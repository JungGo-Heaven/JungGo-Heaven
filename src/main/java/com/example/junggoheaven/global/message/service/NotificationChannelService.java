package com.example.junggoheaven.global.message.service;

import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.entity.NotificationChannel;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.writer.NotificationChannelWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationChannelService {
	private final NotificationChannelWriter notificationChannelWriter;
	private final UserFinder userFinder;

	public void createNotificationChannel(Long userId, String channelType) {
		User user = userFinder.findNonDeletedUserById(userId);

		ChannelType type = ChannelType.valueOf(channelType);
		NotificationChannel notificationChannel = NotificationChannel.of(type, user);
		notificationChannelWriter.write(notificationChannel);
	}
}
