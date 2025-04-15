package com.example.junggoheaven.global.message.event.finder;

import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

import lombok.Getter;

@Getter
public final class InquiryResponseRegisteredEvent extends NotificationEvent {

	public InquiryResponseRegisteredEvent(Object source, Long adminId, String notificationMessage) {
		super(source, adminId, NotificationType.INQUIRY_RESPONSE, notificationMessage);
	}
}
