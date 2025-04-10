package com.example.junggoheaven.domain.chatMessage.dto.request;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChatMessageRequestDto {
    @Nullable
    private Long chatRoomId;

    @NotNull
    private Long productId;

    @NotBlank(message = "메시지 내용을 입력해주세요.")
    private String message;
    @NotBlank
    private MessageType messageType;
}
