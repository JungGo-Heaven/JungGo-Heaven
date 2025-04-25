package com.example.junggoheaven.global.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.service.component.KeywordFinder;
import com.example.junggoheaven.domain.keyword.service.component.KeywordWriter;
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
	private final KeywordFinder keywordFinder;
	private final KeywordWriter keywordWriter;
	private final UserFinder userFinder;

	public void addNotificationChannel(Long userId, String channelType) {
		User user = userFinder.findByUserId(userId);
		List<NotificationChannel> NotiList = notificationChannelFinder.findByUserId(user.getId());

		KeywordDocument savedUser = keywordFinder.findByUserId(userId.toString());

		ChannelType type = ChannelType.valueOf(channelType);

		for (NotificationChannel channel : NotiList) {
			if (channel.getChannelType().equals(type)) {
				return;
			}
		}

		NotificationChannel notificationChannel = NotificationChannel.of(type, user);
		notificationChannelWriter.write(notificationChannel);

		savedUser.addChannel(type);
		keywordWriter.write(savedUser);
	}

	@Transactional
	public void deleteNotificationChannel(Long userId, String channelType) {
		User user = userFinder.findByUserId(userId);
		List<NotificationChannel> NotiList = notificationChannelFinder.findByUserId(user.getId());

		KeywordDocument savedUser = keywordFinder.findByUserId(userId.toString());

		ChannelType type = ChannelType.valueOf(channelType);

		for (NotificationChannel channel : NotiList) {
			if (channel.getChannelType().equals(type)) {
				notificationChannelWriter.delete(channel);
				savedUser.deleteChannel(type);
				keywordWriter.write(savedUser);
				break;
			}
		}
	}
}
