package com.example.junggoheaven.global.message.event.finder;

import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

public class OrderStatusChangeEvent extends NotificationEvent {
	public OrderStatusChangeEvent(Object source, Long userId, String notificationMessage) {
		super(source, userId, NotificationType.ORDER_CHANGE, notificationMessage);
	}
}
