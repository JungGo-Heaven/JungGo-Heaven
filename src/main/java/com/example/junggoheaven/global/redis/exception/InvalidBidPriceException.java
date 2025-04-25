package com.example.junggoheaven.global.redis.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidBidPriceException extends BroadcastException {

    public InvalidBidPriceException(Long auctionId, Long userId) {
        super(RedisErrorCode.INVALID_BID_PRICE, userId, Map.of("auctionId", auctionId));

    }
}
