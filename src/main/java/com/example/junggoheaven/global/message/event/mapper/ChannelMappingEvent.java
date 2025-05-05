package com.example.junggoheaven.global.message.event.mapper;

import java.util.List;
import java.util.Map;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByEmailEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByFCMEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByKakaoEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByWebPushEvent;

import lombok.Getter;

@Getter
public class ChannelMappingEvent extends NotificationEvent {

	public ChannelMappingEvent(Object source, Long userId,
		NotificationType notificationType, String notificationMessage, List<MatchedUserDto> userList) {
		super(source, userId, notificationType, notificationMessage);
		this.getUserList().addAll(userList);
	}

	public Map<ChannelType, NotificationEvent> getNotificationEventMap() {
		return Map.of(
			ChannelType.EMAIL, new PushByEmailEvent(this, this.getUserId(), this.getNotificationType(), this.getNotificationMessage()),
			ChannelType.FCM, new PushByFCMEvent(this, this.getUserId(), this.getNotificationType(), this.getNotificationMessage()),
			ChannelType.WEB_PUSH, new PushByWebPushEvent(this, this.getUserId(), this.getNotificationType(), this.getNotificationMessage()),
			ChannelType.KAKAO_TALK, new PushByKakaoEvent(this, this.getUserId(), this.getNotificationType(), this.getNotificationMessage())
		);
	}
}
