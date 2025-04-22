package com.example.junggoheaven.domain.auction.redis;

import com.example.junggoheaven.domain.auction.dto.response.BroadcastBidResponseDto;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RedisBidSubscriber implements MessageListener { 
    private final SimpMessagingTemplate messagingTemplate;
    private final Gson gson;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        BroadcastBidResponseDto dto = gson.fromJson(msg, BroadcastBidResponseDto.class);

        messagingTemplate.convertAndSend("/sub/auctions/" + dto.getAuctionId(), dto);
    }
}
