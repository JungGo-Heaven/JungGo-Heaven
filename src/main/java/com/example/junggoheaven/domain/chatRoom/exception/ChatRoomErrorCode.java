package com.example.junggoheaven.domain.chatRoom.exception;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ChatRoomErrorCode implements ErrorCode {
    CHAT_ROOM_NOT_FOUND("CHAT_ROOM_NOT_FOUND", HttpStatus.NOT_FOUND, "채팅방이 존재하지 않습니다."),
    CHAT_ROOM_FORBIDDEN("CHAT_ROOM_FORBIDDEN", HttpStatus.FORBIDDEN, "채팅방에 대한 권한이 없습니다.");


    private String code;
    private HttpStatus httpStatus;
    private String message;

    ChatRoomErrorCode(String code, HttpStatus status, String message) {
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
