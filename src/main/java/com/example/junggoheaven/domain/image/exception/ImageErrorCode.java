package com.example.junggoheaven.domain.image.exception;

import static org.springframework.http.HttpStatus.*;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {

    TYPE_MISMATCH("TYPE_MISMATCH", BAD_REQUEST, "파일 형식이 올바르지 않습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDefaultMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
