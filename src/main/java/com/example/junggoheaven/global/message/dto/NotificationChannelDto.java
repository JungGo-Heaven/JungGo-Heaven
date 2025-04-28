package com.example.junggoheaven.global.message.dto;

import java.util.List;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.global.message.enums.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationChannelDto {
	private ChannelType channelType;
	private String token;

	private NotificationChannelDto(KeywordDocument.Channel channel) {
		this.channelType = channel.getChannel();
		this.token = channel.getToken();
	}

	public static List<NotificationChannelDto> ofList(List<KeywordDocument.Channel> channels) {
		return channels.stream().map(NotificationChannelDto::new).toList();
	}
}
