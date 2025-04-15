package com.example.junggoheaven.global.message.dto;

import java.util.Set;

import com.example.junggoheaven.global.message.enums.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchedUserDto {
	private Long userId;
	private String name;
	private Set<ChannelType> channelTypes;
}
