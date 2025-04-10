package com.example.junggoheaven.domain.review.excetion;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCode {
    REVIEW_NOT_FOUND("REVIEW_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다."),
    REVIEW_FORBIDDEN("REVIEW_FORBIDDEN", HttpStatus.FORBIDDEN, "해당 리뷰에 대한 권한이 없습니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    ReviewErrorCode(String code, HttpStatus status, String message) {
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
