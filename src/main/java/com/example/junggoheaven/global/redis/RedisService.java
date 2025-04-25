package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.chatMessage.redis.dto.RedisChatMessageDto;
import com.example.junggoheaven.global.redis.exception.FailedSaveToZsetException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;


    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public void setAuctionTrigger(Auction auction){
        String redisReserveKey = "reserve:start:auction:" + auction.getId();
        LocalDateTime now = LocalDateTime.now();
        stringRedisTemplate.opsForValue().set(redisReserveKey, "trigger", Duration.between(now, auction.getStart_time()));
    }

    public void expire(String key, LocalDateTime endDateTime){
        stringRedisTemplate.expire(key, Duration.between(LocalDateTime.now(), endDateTime));
    }

    public void saveChatMessageToZSet(RedisChatMessageDto dto) {
        try {
            String key = "chat:chatRoom:" + dto.getChatRoomId();
            double score = dto.getSendAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            redisTemplate.opsForZSet().add(key, dto, score);
        } catch (Exception e) {
            throw new FailedSaveToZsetException(dto.getChatRoomId(), dto.getSenderId());
        }
    }

}
