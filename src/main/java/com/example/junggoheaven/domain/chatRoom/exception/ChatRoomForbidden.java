package com.example.junggoheaven.domain.chatRoom.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ChatRoomForbidden extends BaseException {
    public ChatRoomForbidden() {
        super(ChatRoomErrorCode.CHAT_ROOM_FORBIDDEN);
    }
}
