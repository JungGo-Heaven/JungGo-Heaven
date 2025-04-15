package com.example.junggoheaven.global.message.event.finder;

import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

import lombok.Getter;

@Getter
public final class ProductRegisteredEvent extends NotificationEvent {

	public ProductRegisteredEvent(Object source, Long userId, String productName) {
		super(source, userId, NotificationType.PRODUCT_REGISTRATION, productName);
	}
}
