package com.example.junggoheaven.domain.chatMessage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatReadRequestDto {
    private Long chatRoomId;
    private List<Long> readMessageIds;
}
