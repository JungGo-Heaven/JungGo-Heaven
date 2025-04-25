package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class AuctionProductNotFoundException extends BaseException {
    public AuctionProductNotFoundException() {
        super(AuctionErrorCode.AUCTION_PRODUCT_NOT_FOUND);
    }
}
