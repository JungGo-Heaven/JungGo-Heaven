package com.example.junggoheaven.global.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.entity.NotificationChannel;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.service.component.finder.NotificationChannelFinder;
import com.example.junggoheaven.global.message.service.component.writer.NotificationChannelWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationChannelService {

	private final NotificationChannelWriter notificationChannelWriter;
	private final NotificationChannelFinder notificationChannelFinder;
	private final UserFinder userFinder;

	public void createNotificationChannel(Long userId, String channelType) {
		User user = userFinder.findByUserId(userId);
		List<NotificationChannel> NotiList = notificationChannelFinder.findByUserId(user.getId());

		ChannelType type = ChannelType.valueOf(channelType);

		for (NotificationChannel channel : NotiList) {
			if (channel.getChannelType().equals(type)) {
				return;
			}
		}

		NotificationChannel notificationChannel = NotificationChannel.of(type, user);
		notificationChannelWriter.write(notificationChannel);
	}

	@Transactional
	public void deleteNotificationChannel(Long userId, String channelType) {
		User user = userFinder.findByUserId(userId);
		List<NotificationChannel> NotiList = notificationChannelFinder.findByUserId(user.getId());

		ChannelType type = ChannelType.valueOf(channelType);

		for (NotificationChannel channel : NotiList) {
			if (channel.getChannelType().equals(type)) {
				notificationChannelWriter.delete(channel);
				break;
			}
		}
	}
}
