package com.example.junggoheaven.domain.auction.repository;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRespository extends JpaRepository<Bid, Long> {
    Optional<Bid> findTopByAuctionOrderByBidPriceDesc(Auction auction);
}
