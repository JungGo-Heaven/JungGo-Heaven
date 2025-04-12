package com.example.junggoheaven.domain.chatMessage.controller;

import com.example.junggoheaven.domain.chatMessage.dto.request.ChatDeleteRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatMessageRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatReadRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatDeleteNotificationDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatReadNotificationDto;
import com.example.junggoheaven.domain.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatMessageHandler {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/api/v1/chat-rooms/message")

    public void sendMessage(ChatMessageRequestDto chatMessageRequestDto,
                            Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        ChatMessageResponseDto saved = chatMessageService.createMessage(chatMessageRequestDto, userId);

        // 상대방에게 브로드캐스트
        simpMessagingTemplate.convertAndSend(
                "/sub/chat/room/" + chatMessageRequestDto.getChatRoomId(),
                saved
        );
    }

    /**
     * 프론트에서는 방금 사용자의 뷰포트에 실제로 렌더링된,
     * '상대방이 보낸 메시지들'의 ID 목록을 서버로 전송한다.

     * 서버는 요청된 메시지 ID 목록 중 아직 읽지 않은(isRead = false) 메시지를 골라,
     * 해당 메시지들을 isRead = true 처리한다.

     * 이후, 읽힌 메시지들의 ID 목록과 읽은 사람의 ID를
     * "/sub/chat/room/{chatRoomId}" 경로로 WebSocket 브로드캐스트한다.

     * 이 채팅방을 구독하고 있는 A, B 모두 이 알림을 수신하고,
     * 프론트에서는 메시지 ID를 매칭하여 '읽음 표시'를 렌더링한다.
     */
    @MessageMapping("/api/v1/chat-rooms/message/read")
    public void isRead(ChatReadRequestDto requestDto,
                       Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        chatMessageService.isRead(requestDto, userId);

        simpMessagingTemplate.convertAndSend(
                "/sub/chat/room/" + requestDto.getChatRoomId(),
                new ChatReadNotificationDto(userId, requestDto.getReadMessageIds())
        );
    }

    @MessageMapping("/api/v1/chat-rooms/message/delete")
    public void deleteMessage(ChatDeleteRequestDto requestDto, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        chatMessageService.deleteMessage(requestDto, userId);

        simpMessagingTemplate.convertAndSend(
                "/sub/chat/room/" + requestDto.getChatRoomId(),
                new ChatDeleteNotificationDto(userId, requestDto.getDeleteMessageIds())
        );
    }


}
