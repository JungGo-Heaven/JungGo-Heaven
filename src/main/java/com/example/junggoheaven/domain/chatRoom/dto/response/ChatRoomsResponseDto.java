package com.example.junggoheaven.domain.chatRoom.dto.response;

import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomsResponseDto {
    private Long id;
    private String sellerName;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    public static ChatRoomsResponseDto of(ChatRoom chatRoom, ChatMessage lastMessage) {
        return new ChatRoomsResponseDto(
                chatRoom.getId(),
                chatRoom.getProduct().getUser().getName(),
                lastMessage.getMessage(),
                lastMessage.getSendAt()
        );
    }
}
