package com.example.junggoheaven.global.message.event.pusher;

import java.util.ArrayList;
import java.util.List;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

import lombok.Getter;

@Getter
public class PushByFCMEvent extends NotificationEvent {

	public PushByFCMEvent(Object source, Long userId,
		NotificationType notificationType, String notificationMessage) {
		super(source, userId, notificationType, notificationMessage);
	}
}
