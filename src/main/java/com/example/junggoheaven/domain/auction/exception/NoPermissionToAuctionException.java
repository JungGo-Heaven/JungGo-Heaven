package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class NoPermissionToAuctionException extends BaseException {
    public NoPermissionToAuctionException() {
        super(AuctionErrorCode.NO_PERMISSION_TO_AUCTION);
    }
}
