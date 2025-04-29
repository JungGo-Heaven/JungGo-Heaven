package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class BidNotFoundException extends BaseException {
    public BidNotFoundException() {
        super(AuctionErrorCode.BID_NOT_FOUND);
    }
}
