package com.example.junggoheaven.global.message.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.junggoheaven.global.message.entity.NotificationEventLog;
import com.example.junggoheaven.global.message.event.NotificationEvent;
import com.example.junggoheaven.global.message.service.notificationChannel.component.writer.NotificationEventLogWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {
	private final ApplicationEventPublisher eventPublisher;
	private final NotificationEventLogWriter notificationEventLogWriter;

	/*
	 * @Transactional 상태일 경우 해당 Transaction 이 완료 된 이후 이벤트를 발생시키도록 작성.
	 * Keyword 알림의 경우 상품 등록 Transaction 이 성공적으로 Commit 된 후에 발행되어야 하기 떄문이다.
	 * 만약 이벤트가 Transaction 에 참여해야 할 필요가 없다면 상위 메서드에서 @Transactional 를 제거하거나
	 * publishEventWithoutTransaction 메서드를 만들어서 이벤트가 바로 실행되도록 하는 것이 좋다.
	 * */

	public void publishEventAfterTransaction(NotificationEvent event) {
		NotificationEventLog eventLog = NotificationEventLog.builder()
			.userId(event.getUserId())
			.notificationType(event.getNotificationType())
			.message(event.getNotificationMessage())
			.build();

		event.setNotificationEventLog(notificationEventLogWriter.write(eventLog));
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
				@Override
				public void afterCommit() {
					eventPublisher.publishEvent(event);
				}
			});
		} else {
			eventPublisher.publishEvent(event);
		}
	}

	public void publishEvent(NotificationEvent event) {
		NotificationEventLog eventLog = NotificationEventLog.builder()
			.userId(event.getUserId())
			.notificationType(event.getNotificationType())
			.message(event.getNotificationMessage())
			.build();

		event.setNotificationEventLog(notificationEventLogWriter.write(eventLog));
		eventPublisher.publishEvent(event);
	}
}
