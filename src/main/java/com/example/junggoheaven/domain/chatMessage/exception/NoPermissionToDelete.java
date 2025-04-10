package com.example.junggoheaven.domain.chatMessage.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class NoPermissionToDelete extends BaseException {
    public NoPermissionToDelete() {
        super(ChatMessageErrorCode.NO_PERMISSION_TO_DELETE);
    }
}
