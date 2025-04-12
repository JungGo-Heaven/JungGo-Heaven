package com.example.junggoheaven.domain.chatMessage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatDeleteNotificationDto {
    private Long deleterId;
    private List<Long> deletedMessageIds;
}
