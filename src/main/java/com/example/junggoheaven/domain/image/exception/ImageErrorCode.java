package com.example.junggoheaven.domain.image.exception;


import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public enum ImageErrorCode implements ErrorCode {

    TYPE_MISMATCH("TYPE_MISMATCH", BAD_REQUEST, "파일 형식이 올바르지 않습니다."),
    INVALID_FILE_TYPE("INVALID_FILE_TYPE", BAD_REQUEST, "사진 파일만 업로드 가능합니다."),
    UPLOAD_ACCESS_DENIED("UPLOAD_ACCESS_DENIED", BAD_REQUEST, "업로드 권한이 없습니다."),
    FILE_UPLOAD_LIMIT_EXCEEDED("FILE_UPLOAD_LIMIT_EXCEEDED", BAD_REQUEST, "파일 업로드 가능 개수를 초과했습니다."),
    INVALID_UPLOAD_TYPE("INVALID_UPLOAD_TYPE", BAD_REQUEST, "요청하신 파일 타입이 아닙니다."),
    IMAGE_UPLOAD_IO_EXCEPTION("IMAGE_UPLOAD_IO_EXCEPTION", INTERNAL_SERVER_ERROR, "이미지 저장 중 문제가 발생했습니다."),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.");

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
