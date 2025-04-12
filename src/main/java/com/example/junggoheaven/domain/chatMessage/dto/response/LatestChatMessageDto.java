package com.example.junggoheaven.domain.chatMessage.dto.response;

import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;

public record LatestChatMessageDto(Long chatRoomId, ChatMessage chatMessage) {}