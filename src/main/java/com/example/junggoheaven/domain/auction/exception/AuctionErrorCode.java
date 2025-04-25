package com.example.junggoheaven.domain.auction.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuctionErrorCode implements ErrorCode {
    //AuctionProduct
    AUCTION_PRODUCT_NOT_FOUND("AUCTION_PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND, "경매상품을 불러오는 것에 실패했습니다."),
    NO_PERMISSION_TO_DELETE("NO_PERMISSION_TO_DELETE", HttpStatus.FORBIDDEN, "경매상품을 삭제할 권한이 없습니다."),

    //Auction
    AUCTION_NOT_FOUND("AUCTION_NOT_FOUND", HttpStatus.NOT_FOUND, "경매를 불러오는 것에 실패했습니다."),
    INVALID_AUCTION_START_TIME("INVALID_AUCTION_START_TIME", HttpStatus.BAD_REQUEST, "경매 시작 시간은 현재보다 이후여야 합니다."),
    NO_PERMISSION_TO_AUCTION("NO_PERMISSION_TO_AUCTION", HttpStatus.FORBIDDEN, "해당 경매에 대한 권한이 없습니다."),
    INVALID_AUCTION_STATUS("INVALID_AUCTION_STATUS", HttpStatus.BAD_REQUEST, "진행 중인 경매가 아닙니다."),

    //Bid
    BID_NOT_FOUND("BID_NOT_FOUND", HttpStatus.NOT_FOUND, "입찰을 불러오는 것에 실패했습니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    AuctionErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.httpStatus = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getDefaultMessage() {
        return this.message;
    }
}
