package com.example.junggoheaven.global.message.event.pusher;


import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

import lombok.Getter;

@Getter
public final class PushByEmailEvent extends NotificationEvent {

	public PushByEmailEvent(Object source, Long userId, NotificationType notificationType, String notificationMessage) {
		super(source, userId, notificationType, notificationMessage);
	}
}
