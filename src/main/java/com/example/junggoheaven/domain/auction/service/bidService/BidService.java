package com.example.junggoheaven.domain.auction.service.bidService;

import com.example.junggoheaven.domain.auction.dto.response.BroadcastBidResponseDto;
import com.example.junggoheaven.domain.auction.dto.response.EnterBidResponseDto;
import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.enums.AuctionStatus;
import com.example.junggoheaven.domain.auction.exception.InvalidAuctionStatusException;
import com.example.junggoheaven.domain.auction.redis.RedisBidPublisher;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidFinder;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final AuctionFinder auctionFinder;
    private final UserFinder userFinder;
    private final BidWriter bidWriter;
    private final BidFinder bidFinder;
    private final RedisBidPublisher redisBidPublisher;

    @Transactional(readOnly = true)
    public EnterBidResponseDto enterBid(Long auctionId){
        Auction auction = auctionFinder.findAuctionById(auctionId);
        if(!auction.getStatus().equals(AuctionStatus.ONGOING)){
            throw new InvalidAuctionStatusException();
        }
        Optional<Bid> bid = bidFinder.findTopByAuctionOrderByBidPriceDesc(auction);

        return EnterBidResponseDto.of(auction, bid.orElse(null));
    }

    @Transactional
    public void createBid(Long authUserId, Long auctionId, Integer bidPrice){
//        Auction auction = auctionFinder.findAuctionByIdForBid(auctionId); //비관적락
        Auction auction = auctionFinder.findAuctionById(auctionId);
        User bidder = userFinder.findByUserId(authUserId);

        Bid bid = Bid.of(auction, bidder, bidPrice);
        Bid savedBid = bidWriter.save(bid);

        BroadcastBidResponseDto broadcastDto = new BroadcastBidResponseDto(
                auctionId,
                bidPrice,
                bidder.getName(),
                savedBid.getBidAt()
        );

        redisBidPublisher.publish("auction.broadcast", broadcastDto);
    }

}
