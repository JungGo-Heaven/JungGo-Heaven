package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailedToAcquireLockException extends BaseException {
    private final Long auctionId;
    private final Long userId;
    public FailedToAcquireLockException(Long auctionId, Long userId) {
        super(RedisErrorCode.FAILED_TO_ACQUIRE_LOCK);
        this.auctionId = auctionId;
        this.userId = userId;
    }
}
