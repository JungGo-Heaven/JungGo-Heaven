package com.example.junggoheaven.domain.image.exception.imageException;


import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum ImageErrorCode implements ErrorCode {

    TYPE_MISMATCH("TYPE_MISMATCH", BAD_REQUEST, "파일 형식이 올바르지 않습니다."),
    INVALID_FILE_TYPE("INVALID_FILE_TYPE", BAD_REQUEST, "사진 파일만 업로드 가능합니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    ImageErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

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
