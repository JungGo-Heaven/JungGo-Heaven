package com.example.junggoheaven.global.redis.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public abstract class BroadcastException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final Long userId;
    private final Map<String, Object> context;


    protected BroadcastException(ErrorCode errorCode, Long userId, Map<String, Object> context) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.status = errorCode.getHttpStatus();
        this.userId = userId;
        this.context = context;

    }
}