package com.example.junggoheaven.global.redis.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class FailedSaveToZsetException extends BroadcastException {
    public FailedSaveToZsetException(Long chatRoomId, Long userId) {
        super(RedisErrorCode.FAILED_SAVE_TO_ZSET, userId, Map.of("chatRoomId", chatRoomId));
    }
}
