package com.example.junggoheaven.global.redis;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionWriter;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Slf4j
public class RedisKeyExpirationListner extends KeyExpirationEventMessageListener {
    private final AuctionFinder auctionFinder;
    private final AuctionWriter auctionWriter;
    private final RedisService redisService;
    private final BidFinder bidFinder;
    private final UserFinder userFinder;

    public RedisKeyExpirationListner(AuctionFinder auctionFinder,
                                     AuctionWriter auctionWriter,
                                     RedisService redisService,
                                     BidFinder bidFinder,
                                     UserFinder userFinder,
                                     RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
        this.auctionFinder = auctionFinder;
        this.auctionWriter = auctionWriter;
        this.redisService = redisService;
        this.bidFinder = bidFinder;
        this.userFinder = userFinder;
    }

    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody()); //message는 body와 getChannel이 key만료 이벤트에 대한 정보가 byte배열로 담겨있음. body에는 reserve:start:auction:{auctionId} 형식으로 되어있다.
        if (expiredKey.startsWith("reserve:start:auction:")) {
            Long auctionId = Long.parseLong(expiredKey.split(":")[3]);
            Auction auction = auctionFinder.findAuctionById(auctionId);
            auction.startAuction();
            auctionWriter.save(auction);
            String bidKey = "bid:auction:" + auction.getId();

            redisService.set(bidKey, String.valueOf(auction.getStart_price()));
            redisService.expire(bidKey, auction.getEnd_time());
        }

        if(expiredKey.startsWith("bid:auction:")) {
            Long auctionId = Long.parseLong(expiredKey.split(":")[2]);
            Auction auction = auctionFinder.findAuctionById(auctionId);
            Optional<Bid> bid = bidFinder.findTopByAuctionOrderByBidPriceDesc(auction);
            if(!bid.isPresent()) {
                auction.failAuction();
            } else{
                auction.successfulAuction();
                User user = userFinder.findByUserId(bid.get().getBidder().getId());
                auction.updateWinner(user);
            }
            auctionWriter.save(auction);
        }
    }
}
