package com.example.junggoheaven.global.message.event.finder;

import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

public class LocationVerificationEvent extends NotificationEvent {
	public LocationVerificationEvent(Object source, Long userId, String notificationMessage) {
		super(source, userId, NotificationType.LOCATION_VERIFICATION, notificationMessage);
	}
}
