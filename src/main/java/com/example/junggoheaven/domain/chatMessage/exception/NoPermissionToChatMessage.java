package com.example.junggoheaven.domain.chatMessage.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class NoPermissionToChatMessage extends BaseException {
    public NoPermissionToChatMessage() {
        super(ChatMessageErrorCode.NO_PERMISSION_TO_CHATMESSAGE);
    }
}
