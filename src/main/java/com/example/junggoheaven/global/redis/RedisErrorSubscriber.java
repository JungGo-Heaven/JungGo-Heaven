package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.global.redis.dto.ErrorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisErrorSubscriber implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final GenericJackson2JsonRedisSerializer serializer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ErrorPayload payload = (ErrorPayload) serializer.deserialize(message.getBody());

        messagingTemplate.convertAndSendToUser(
                payload.getUserId().toString(),
                "/queue/errors",
                payload
        );
    }
}