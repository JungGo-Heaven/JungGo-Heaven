package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidFinder;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
public class RedisKeyExpirationListner extends KeyExpirationEventMessageListener {
    private final AuctionFinder auctionFinder;
    private final RedisService redisService;
    private final BidFinder bidFinder;

    public RedisKeyExpirationListner(AuctionFinder auctionFinder,
                                     RedisService redisService,
                                     BidFinder bidFinder,
                                     RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
        this.auctionFinder = auctionFinder;
        this.redisService = redisService;
        this.bidFinder = bidFinder;
    }

    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody()); //message는 body와 getChannel이 key만료 이벤트에 대한 정보가 byte배열로 담겨있음. body에는 reserve:start:auction:{auctionId} 형식으로 되어있다.
        if (expiredKey.startsWith("reserve:start:auction:")) {
            Long auctionId = Long.parseLong(expiredKey.split(":")[3]);
            Auction auction = auctionFinder.findAuctionById(auctionId);

            String bidKey = "bid:auction:" + auction.getId();

            redisService.set(bidKey, String.valueOf(auction.getStart_price()));
            redisService.expire(bidKey, auction.getEnd_time());
        }

        if(expiredKey.startsWith("bid:auction:")) {
            Long auctionId = Long.parseLong(expiredKey.split(":")[2]);
            Auction auction = auctionFinder.findAuctionById(auctionId);
            //낙찰자 선정 로직 필요함.
            bidFinder.findTopByAuctionOrderByBidPriceDesc(auction);
        }
    }
}
