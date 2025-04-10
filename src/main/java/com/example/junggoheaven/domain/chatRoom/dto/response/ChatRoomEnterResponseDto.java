package com.example.junggoheaven.domain.chatRoom.dto.response;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEnterResponseDto {
    private List<ChatMessageResponseDto> messages;
}
