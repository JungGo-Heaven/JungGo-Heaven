package com.example.junggoheaven.domain.auction.service.auctionService.component;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionWriter {
    private final AuctionRepository auctionRepository;

    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    public void delete(Auction auction) {
        auctionRepository.delete(auction);
    }
}
