package com.example.junggoheaven.domain.auction.service.auctionService.component;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.exception.AuctionNotFoundException;
import com.example.junggoheaven.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionFinder {
    private final AuctionRepository auctionRepository;

    public Page<Auction> findPagingAuctions(Pageable pageable) {
        return auctionRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Auction findAuctionById(Long id) {
        Auction auction = auctionRepository.findAuctionById(id);
        if (auction == null) {
            throw new AuctionNotFoundException();
        }
        return auction;
    }
}
