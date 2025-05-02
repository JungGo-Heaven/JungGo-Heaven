package com.example.junggoheaven.domain.auction.redis;

import com.example.junggoheaven.domain.auction.dto.response.BroadcastBidResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RedisBidSubscriber implements MessageListener { 
    private final SimpMessagingTemplate messagingTemplate;
    private final GenericJackson2JsonRedisSerializer serializer;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        BroadcastBidResponseDto dto = (BroadcastBidResponseDto)
                serializer.deserialize(message.getBody());
        messagingTemplate.convertAndSend("/sub/auctions/" + dto.getAuctionId(), dto);
    }
}
