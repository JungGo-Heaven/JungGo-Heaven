package com.example.junggoheaven.global.message.service.component.writer;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.entity.NotificationDeliveryLog;
import com.example.junggoheaven.global.message.repository.NotificationDeliveryLogRepository;

import lombok.RequiredArgsConstructor;

/*
* 이벤트 발송에 대한 데이터를 Log 로 기록하는 Component
* 1. 이벤트 ID
* 2. 이벤트 발송 상태
* 3. 이벤트 체널 (Email, Web Push, FCM, 카카오톡 등등)
* 4. ERROR 메세지
* */

@Component
@RequiredArgsConstructor
public class NotificationDeliveryEventWriter {
	private final NotificationDeliveryLogRepository notificationDeliveryLogRepository;

	public void write (NotificationDeliveryLog notificationDeliveryLog) {
		notificationDeliveryLogRepository.save(notificationDeliveryLog);
	}
}
