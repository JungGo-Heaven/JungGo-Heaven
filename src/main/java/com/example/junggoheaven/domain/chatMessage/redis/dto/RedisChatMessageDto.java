package com.example.junggoheaven.domain.chatMessage.redis.dto;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RedisChatMessageDto implements Serializable {
    private Long chatRoomId;
    private Long senderId;
    private Long chatMessageId;
    private String message;
    private String imageUrl; // 이미지 url
    private String keyName; //s3 key
    private LocalDateTime sendAt;
    private MessageType messageType; //enum
    private Boolean isRead;

    public RedisChatMessageDto(Long chatRoomId, Long userId, Long chatMessageId, String message, String imageUrl, String keyName, MessageType messageType) {
        this.chatRoomId = chatRoomId;
        this.senderId = userId;
        this.chatMessageId = chatMessageId;
        this.message = message;
        this.imageUrl = imageUrl;
        this.keyName = keyName;
        this.sendAt = LocalDateTime.now();
        this.messageType = messageType;
        this.isRead = false;
    }

}