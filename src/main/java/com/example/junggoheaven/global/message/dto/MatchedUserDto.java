package com.example.junggoheaven.global.message.dto;

import java.util.List;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchedUserDto {
	private Long userId;
	private String email;
	private List<NotificationChannelDto> channels;

	public static MatchedUserDto of(KeywordDocument keywordDocument) {
		return new MatchedUserDto(
			Long.parseLong(keywordDocument.getId()),
			keywordDocument.getEmail(),
			NotificationChannelDto.ofList(keywordDocument.getChannels()));
	}
}
