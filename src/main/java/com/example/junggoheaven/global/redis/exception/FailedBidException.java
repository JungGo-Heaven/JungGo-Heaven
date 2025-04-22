package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class FailedBidException extends BaseException {
    private final Long auctionId;
    private final Long userId;
    public FailedBidException(Long auctionId, Long userId) {
        super(RedisErrorCode.FAILED_BID);
        this.auctionId = auctionId;
        this.userId = userId;

    }
}
