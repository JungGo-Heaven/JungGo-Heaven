package com.example.junggoheaven.domain.chatMessage.exception;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.global.common.exception.BaseException;

public class MessageNotFoundException extends BaseException {
    public MessageNotFoundException() {
        super(ChatMessageErrorCode.CHAT_NOT_FOUND);
    }
}
