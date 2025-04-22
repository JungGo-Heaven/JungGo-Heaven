package com.example.junggoheaven.global.message.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.event.mapper.ChannelMappingEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByEmailEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByFCMEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByKakaoEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByWebPushEvent;
import com.example.junggoheaven.global.message.publisher.EventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
* 사용자 (User) 가 알림 등록한 체널을 분류하는 Component
* 분류 후 해당 Pusher 에 UserList 를 전달한다.
* */

@Slf4j
@Component
@RequiredArgsConstructor
public class UserChannelMapper {
	private final EventPublisher eventPublisher;

	@Async
	@EventListener
	public void channelMapper(ChannelMappingEvent event) {
		List<MatchedUserDto> userList = event.getUserList();
		PushByEmailEvent email = new PushByEmailEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage());
		PushByKakaoEvent kakao = new PushByKakaoEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage());
		PushByFCMEvent fcm = new PushByFCMEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage());
		PushByWebPushEvent web = new PushByWebPushEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage());

		userList.forEach(user -> {
			Set<ChannelType> channelTypes = user.getChannelTypes();
			for (ChannelType channelType : channelTypes) {
				switch (channelType) {
					case KAKAO_TALK:
						kakao.getUserList().add(user);
						break;
					case EMAIL:
						email.getUserList().add(user);
						break;
					case FCM:
						fcm.getUserList().add(user);
						break;
					case WEB_PUSH:
						web.getUserList().add(user);
						break;
				}
			}
		});

		log.info("총 사용자 수: {}, Email 사용자 수: {}, KakaoTalk 사용자 수: {}, FCM 사용자 수: {}, Web Push 사용자 수: {}",
			userList.size(),
			email.getUserList().size(),
			kakao.getUserList().size(),
			fcm.getUserList().size(),
			web.getUserList().size()
		);

		eventPublisher.publishEvent(email);
		eventPublisher.publishEvent(kakao);
		eventPublisher.publishEvent(fcm);
		eventPublisher.publishEvent(web);
	}
}
