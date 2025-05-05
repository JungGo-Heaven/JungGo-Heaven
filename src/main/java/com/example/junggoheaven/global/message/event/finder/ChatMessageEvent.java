package com.example.junggoheaven.global.message.event.finder;

import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

public class ChatMessageEvent extends NotificationEvent {
	public ChatMessageEvent(Object source, Long userId, String notificationMessage) {
		super(source, userId, NotificationType.CHAT_MESSAGE, notificationMessage);
	}
}
