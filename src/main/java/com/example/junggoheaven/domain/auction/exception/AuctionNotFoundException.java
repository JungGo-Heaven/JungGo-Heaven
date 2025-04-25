package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class AuctionNotFoundException extends BaseException {
    public AuctionNotFoundException() {
        super(AuctionErrorCode.AUCTION_NOT_FOUND);
    }
}
