package com.example.junggoheaven.domain.chatMessage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDto {
    private Long chatRoomId;
    private Long messageId;
    private Long senderId;
    private String message;
    private LocalDateTime sendAt;
}
