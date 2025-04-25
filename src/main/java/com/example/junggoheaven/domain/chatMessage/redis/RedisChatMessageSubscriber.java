package com.example.junggoheaven.domain.chatMessage.redis;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatDeleteNotificationDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatReadNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RedisChatMessageSubscriber implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final GenericJackson2JsonRedisSerializer serializer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Object deserialized = serializer.deserialize(message.getBody());

        if (deserialized instanceof ChatMessageResponseDto msgDto) {
            messagingTemplate.convertAndSend("/sub/chat/room/" + msgDto.getChatRoomId(), msgDto);
        }
        if (deserialized instanceof ChatReadNotificationDto readDto) {
            messagingTemplate.convertAndSend("/sub/chat/room/" + readDto.getChatRoomId(), readDto);
        }
        if (deserialized instanceof ChatDeleteNotificationDto deleteDto) {
            messagingTemplate.convertAndSend("/sub/chat/room/" + deleteDto.getChatRoomId(), deleteDto);
        }
    }
}
