package com.example.junggoheaven.domain.auction.redis;

import com.example.junggoheaven.domain.auction.dto.response.BroadcastBidResponseDto;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisBidPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, BroadcastBidResponseDto message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
