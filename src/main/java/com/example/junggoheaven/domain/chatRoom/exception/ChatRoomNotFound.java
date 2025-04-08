package com.example.junggoheaven.domain.chatRoom.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ChatRoomNotFound extends BaseException {
    public ChatRoomNotFound() {
        super(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND);
    }
}
