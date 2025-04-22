package com.example.junggoheaven.domain.auction.rabbitMQ;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.rabbitMQ.dto.BidMessage;
import com.example.junggoheaven.global.redis.exception.InvalidBidPriceException;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.bidService.BidService;
import com.example.junggoheaven.global.config.RabbitMQConfig;
import com.example.junggoheaven.global.redis.RedisService;
import com.example.junggoheaven.global.redis.exception.FailedBidException;
import com.example.junggoheaven.global.redis.exception.FailedToAcquireLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class BidConsumer {

    private final BidService bidService;
    private final RedissonClient redissonClient;
    private final RedisService redisService;
    private final AuctionFinder auctionFinder;

    @RabbitListener(queues = RabbitMQConfig.BID_QUEUE)
    public void consume(BidMessage message) {
        String lockKey = "lock:auction:" + message.auctionId();
        String bidKey = "bid:auction:" + message.auctionId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (!lock.tryLock(3, 1, TimeUnit.SECONDS)) {
                throw new FailedToAcquireLockException(message.auctionId(), message.userId());
            }

            // Redis에서 현재 최고가 조회
            String current = redisService.get(bidKey);
            int currentPrice = current != null ? Integer.parseInt(current) : -1;

            if (message.bidPrice() <= currentPrice) {
                throw new InvalidBidPriceException(message.auctionId(), message.userId());
            }
            Auction auction = auctionFinder.findAuctionById(message.auctionId());

            // 최고가 레디스에 저장
            redisService.set(bidKey, String.valueOf(message.bidPrice()));
            redisService.expire(bidKey, auction.getEnd_time());

            bidService.createBid(message.userId(), message.auctionId(), message.bidPrice());
        } catch (Exception e){
            log.error(" 입찰 처리 중 예외 발생 - message: {}", message, e);
            throw new FailedBidException(message.auctionId(), message.userId());
        }
    }
}