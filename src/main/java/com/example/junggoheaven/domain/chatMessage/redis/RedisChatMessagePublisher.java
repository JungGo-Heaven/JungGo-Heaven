package com.example.junggoheaven.domain.chatMessage.redis;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatDeleteNotificationDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatReadNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisChatMessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;


    public void publish(String channel, ChatMessageResponseDto message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public void publishRead(String channel, ChatReadNotificationDto dto) {
        redisTemplate.convertAndSend(channel, dto);
    }

    public void publishDelete(String channel, ChatDeleteNotificationDto dto) {
        redisTemplate.convertAndSend(channel, dto);
    }
}
