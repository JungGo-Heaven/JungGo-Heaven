package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class InvalidAuctionStatusException extends BaseException {
    public InvalidAuctionStatusException() {
        super(AuctionErrorCode.INVALID_AUCTION_STATUS);
    }
}
