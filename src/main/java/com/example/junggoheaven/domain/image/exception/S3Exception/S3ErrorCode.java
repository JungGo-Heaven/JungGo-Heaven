package com.example.junggoheaven.domain.image.exception.S3Exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public enum S3ErrorCode implements ErrorCode {

    AMAZON_SERVICE_ERROR("AMAZON_SERVICE_ERROR", BAD_REQUEST, "아마존 서비스 에러로 인해 PresignedUrl 생성 실패"),
    SDK_CLIENT_ERROR("SDK_CLIENT_ERROR", BAD_REQUEST, "SDK Client 에러로 인해 PresignedUrl 생성 실패"),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", BAD_REQUEST, "알 수 없는 에러로 인해 PresignedUrl 생성 실패");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    S3ErrorCode(String code, HttpStatus httpStatus, String message) {
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
