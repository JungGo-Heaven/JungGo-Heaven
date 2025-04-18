package com.example.junggoheaven.domain.chatMessage.dto.response;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
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
    private String imageUrl;
    private LocalDateTime sendAt;
    private MessageType messageType;
}
