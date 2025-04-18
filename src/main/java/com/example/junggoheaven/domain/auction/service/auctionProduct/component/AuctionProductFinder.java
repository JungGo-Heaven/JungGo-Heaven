package com.example.junggoheaven.domain.auction.service.auctionProduct.component;

import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.exception.AuctionProductNotFoundException;
import com.example.junggoheaven.domain.auction.repository.AuctionProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionProductFinder {
    private final AuctionProductRepository auctionProductRepository;

    public AuctionProduct findByAuctionProductId(Long id) {
        return auctionProductRepository.findById(id).orElseThrow(AuctionProductNotFoundException::new);
    }
}
