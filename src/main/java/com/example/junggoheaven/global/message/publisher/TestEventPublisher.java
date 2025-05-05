package com.example.junggoheaven.global.message.publisher;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.dto.NotificationChannelDto;
import com.example.junggoheaven.global.message.entity.NotificationChannel;
import com.example.junggoheaven.global.message.enums.ChannelType;
import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.finder.OrderStatusChangeEvent;
import com.example.junggoheaven.global.message.event.finder.ProductRegisteredEvent;
import com.example.junggoheaven.global.message.event.pusher.PushByEmailEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestEventPublisher {
	private final EventPublisher eventPublisher;

	public void publishProductRegisteredEvent(String name) {
		eventPublisher.publishEvent(new ProductRegisteredEvent(this, 1L, name));
	}

	public void publishOrderStatusChangedEvent(Long userId) {
		eventPublisher.publishEvent(new OrderStatusChangeEvent(this, userId, "DONE"));
	}

	public void publishPushByEmailEvent(Long userId) {
		PushByEmailEvent event = new PushByEmailEvent(this, userId, NotificationType.PRODUCT_REGISTRATION, "Email");

		List<KeywordDocument.Channel> channels = List.of(
			KeywordDocument.Channel.of(ChannelType.EMAIL, "ahkiler@naver.com"));

		MatchedUserDto dto1 = new MatchedUserDto(userId, "ahkiler@naver.com",
			NotificationChannelDto.ofList(channels));
		MatchedUserDto dto2 = new MatchedUserDto(userId, "ahkiler@naver.com",
			NotificationChannelDto.ofList(channels));
		MatchedUserDto dto3 = new MatchedUserDto(userId, "ahkiler@naver.com",
			NotificationChannelDto.ofList(channels));

		event.getUserList().addAll(List.of(dto1, dto2, dto3));

		eventPublisher.publishEvent(event);
	}
}
