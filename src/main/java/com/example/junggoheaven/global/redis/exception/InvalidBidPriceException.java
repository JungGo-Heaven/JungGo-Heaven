package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import lombok.Getter;

@Getter
public class InvalidBidPriceException extends BaseException {
    private final Long auctionId;
    private final Long userId;
    public InvalidBidPriceException(Long auctionId, Long userId) {
        super(RedisErrorCode.INVALID_BID_PRICE);
        this.auctionId = auctionId;
        this.userId = userId;
    }
}
