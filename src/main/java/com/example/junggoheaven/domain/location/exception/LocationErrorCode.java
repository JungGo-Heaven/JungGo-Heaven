package com.example.junggoheaven.domain.location.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum LocationErrorCode implements ErrorCode {

    ADDRESS_RESPONSE_EMPTY("ADDRESS_RESPONSE_EMPTY", BAD_REQUEST, "주소 결과가 없습니다."),
    COORDINATE_CONVERSION_FAILED("COORDINATE_CONVERSION_FAILED", INTERNAL_SERVER_ERROR, "좌표 변환에 실패했습니다."),
    NOT_IN_MY_TOWN("NOT_IN_MY_TOWN", BAD_REQUEST, "내 동네가 아닙니다."),
    LOCATION_VERIFICATION_REQUIRED("LOCATION_VERIFICATION_REQUIRED", FORBIDDEN, "내 동네 인증이 필요합니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    LocationErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
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
