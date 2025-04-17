package com.example.junggoheaven.global.message.service.component.writer;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.entity.NotificationEventLog;
import com.example.junggoheaven.global.message.repository.NotificationEventLogRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventLogWriter {
	private final NotificationEventLogRepository notificationEventLogRepository;

	public NotificationEventLog write(NotificationEventLog notificationEventLog) {
		return notificationEventLogRepository.save(notificationEventLog);
	}
}
