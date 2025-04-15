package com.example.junggoheaven.global.message.event.mapper;

import java.util.List;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.enums.NotificationType;
import com.example.junggoheaven.global.message.event.NotificationEvent;

import lombok.Getter;

@Getter
public class ChannelMappingEvent extends NotificationEvent {
	private final List<MatchedUserDto> userList;

	public ChannelMappingEvent(Object source, Long userId,
		NotificationType notificationType, String notificationMessage, List<MatchedUserDto> userList) {
		super(source, userId, notificationType, notificationMessage);
		this.userList = userList;
	}
}
