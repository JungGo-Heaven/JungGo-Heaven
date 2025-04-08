package com.example.junggoheaven.domain.chatMessage.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ChatMessageErrorCode implements ErrorCode
{
    CHAT_NOT_FOUND("CHAT_NOT_FOUND", HttpStatus.NOT_FOUND, "채팅내용을 불러오는 것에 실패했습니다."),
    NO_PERMISSION_TO_DELETE("NO_PERMISSION_TO_DELETE", HttpStatus.FORBIDDEN, "메시지를 삭제할 권한이 없습니다.");

    private String code;
    private HttpStatus httpStatus;
    private String message;

    ChatMessageErrorCode(String code, HttpStatus status, String message) {
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
