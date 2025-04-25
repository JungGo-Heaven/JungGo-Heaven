package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import lombok.Getter;

import java.util.Map;

@Getter
public class FailedToAcquireLockException extends BroadcastException {
    public FailedToAcquireLockException(Long auctionId, Long userId) {
        super(RedisErrorCode.INVALID_BID_PRICE, userId, Map.of("auctionId", auctionId));
    }
}
