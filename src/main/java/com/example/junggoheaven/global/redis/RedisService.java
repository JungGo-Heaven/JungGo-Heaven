package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.domain.auction.entity.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void setAuctionTrigger(Auction auction){
        String redisReserveKey = "reserve:start:auction:" + auction.getId();
        LocalDateTime now = LocalDateTime.now();
        redisTemplate.opsForValue().set(redisReserveKey, "trigger", Duration.between(now, auction.getStart_time()));
    }

    public void expire(String key, LocalDateTime endDateTime){
        redisTemplate.expire(key, Duration.between(LocalDateTime.now(), endDateTime));
    }


}
