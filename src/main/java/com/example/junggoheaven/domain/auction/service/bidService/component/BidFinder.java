package com.example.junggoheaven.domain.auction.service.bidService.component;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.repository.BidRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidFinder {
    private final BidRespository bidRespository;

    public Optional<Bid> findTopByAuctionOrderByBidPriceDesc(Auction auction){
        return bidRespository.findTopByAuctionOrderByBidPriceDesc(auction);
    }
}
