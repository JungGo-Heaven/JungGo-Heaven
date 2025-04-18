package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class InvalidAuctionStartTimeException extends BaseException {
    public InvalidAuctionStartTimeException() {
        super(AuctionErrorCode.INVALID_AUCTION_START_TIME);
    }
}
