package com.example.junggoheaven.domain.chatMessage.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ChatRoomMissMatchException extends BaseException {
    public ChatRoomMissMatchException() {
        super(ChatMessageErrorCode.CHAT_ROOM_MISSMATCH);
    }
}
