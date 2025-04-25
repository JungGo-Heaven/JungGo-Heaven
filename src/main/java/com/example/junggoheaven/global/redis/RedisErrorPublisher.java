package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import com.example.junggoheaven.global.redis.dto.ErrorPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisErrorPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void broadcastError(ErrorCode code, HttpStatus status, String message, Long userId, Map<String, Object> context) {
        ErrorPayload payload = new ErrorPayload(code, status.value(), message, userId, context);
        redisTemplate.convertAndSend("error.broadcast", payload);
    }
}
