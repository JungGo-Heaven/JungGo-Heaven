package com.example.junggoheaven.domain.auction.rabbitMQ;

import com.example.junggoheaven.domain.auction.rabbitMQ.dto.BidMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendBid(BidMessage bidMessage) {
        rabbitTemplate.convertAndSend("bid.exchange", "bid.key", bidMessage);
    }
}