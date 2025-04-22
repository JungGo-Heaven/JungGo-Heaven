package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum RedisErrorCode implements ErrorCode {

    FAILED_TO_ACQUIRE_LOCK("FAILED_TO_ACQUIRE_LOCK", HttpStatus.LOCKED, "현재 서버에 요청이 많습니다. 다시 시도해주세요"),
    FAILED_BID("FAILED_BID", HttpStatus.BAD_REQUEST, "입찰에 실패하였습니다."),
    INVALID_BID_PRICE("INVALID_BID_PRICE", HttpStatus.BAD_REQUEST, "입찰 금액은 최고가 이상이어야 합니다.");



    private String code;
    private HttpStatus httpStatus;
    private String message;

    RedisErrorCode(String code, HttpStatus status, String message) {
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

