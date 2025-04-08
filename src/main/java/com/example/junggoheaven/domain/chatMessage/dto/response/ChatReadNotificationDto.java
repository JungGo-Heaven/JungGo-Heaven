package com.example.junggoheaven.domain.chatMessage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatReadNotificationDto {
    private Long readerId;
    private List<Long> readMessageIds;
}
