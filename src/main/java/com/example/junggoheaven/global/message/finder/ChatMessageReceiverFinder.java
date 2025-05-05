package com.example.junggoheaven.global.message.finder;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.event.finder.ChatMessageEvent;
import com.example.junggoheaven.global.message.event.mapper.ChannelMappingEvent;
import com.example.junggoheaven.global.message.publisher.EventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageReceiverFinder {
	private final EventPublisher eventPublisher;
	private final UserFinder userFinder;

	@Async
	@EventListener
	public void findChatMessageReceiver(ChatMessageEvent event) {
		List<MatchedUserDto> userList = List.of(userFinder.findMatchedUserDtoById(event.getUserId()));

		eventPublisher.publishEvent( new ChannelMappingEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage(), userList));
	}
}
