package com.example.junggoheaven.domain.auction.controller;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BidErrorBroadcaster {
    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastError(ErrorCode code, HttpStatus status, String message, Long auctionId, Long userId) {
        Map<String, Object> errorPayload = Map.of(
                "code", code,
                "status", status.value(),
                "message", message,
                "auctionId", auctionId
        );
        messagingTemplate.convertAndSend(userId.toString(), // 유저 식별자
                "/queue/errors",   // 유저의 개인 채널 (prefix 포함)
                errorPayload);
    }
}
