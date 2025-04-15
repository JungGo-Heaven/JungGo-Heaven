package com.example.junggoheaven.global.message.pusher;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.entity.NotificationDeliveryLog;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.event.pusher.PushByEmailEvent;
import com.example.junggoheaven.global.message.service.component.writer.NotificationDeliveryEventWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailPusher {
	private final NotificationDeliveryEventWriter notificationDeliveryEventWriter;

	@Async
	@EventListener
	public void productRegisteredMessagePusher(PushByEmailEvent event) {
		List<MatchedUserDto> userList = event.getUserList();
		int count = 0;
		for (MatchedUserDto user : userList) {
			// push 알림
			count++;
		}

		// 알림 방식 로그로 저장
		log.info("총 {} 명의 사용자 에게 알림 {} 을/를 보냈습니다.", count, ChannelType.EMAIL);

		NotificationDeliveryLog notificationDeliveryLog = NotificationDeliveryLog.builder()
			.channelType(ChannelType.EMAIL)
			.status(NotificationDeliveryLog.Status.SUCCESS)
			.errorMessage(NotificationDeliveryLog.ErrorMessage.NULL)
			.notificationEventLog(event.getNotificationEventLog())
			.build();

		notificationDeliveryEventWriter.write(notificationDeliveryLog);
	}
}
