package com.example.junggoheaven.global.message.event;

import org.springframework.context.ApplicationEvent;

import com.example.junggoheaven.global.message.entity.NotificationEventLog;
import com.example.junggoheaven.global.message.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NotificationEvent extends ApplicationEvent {
	private final Long userId;
	private final NotificationType notificationType;
	private final String notificationMessage;
	@Setter
	private NotificationEventLog notificationEventLog;

	public NotificationEvent(Object source, Long userId, NotificationType notificationType, String notificationMessage) {
		super(source);
		this.userId = userId;
		this.notificationType = notificationType;
		this.notificationMessage = notificationMessage;
	}
}
