package com.example.junggoheaven.domain.auction.service.auctionProduct.component;

import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.repository.AuctionProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionProductWriter {
    private final AuctionProductRepository auctionProductRepository;

    public AuctionProduct save(AuctionProduct auctionProduct) {
        return auctionProductRepository.save(auctionProduct);
    }

    public void delete(AuctionProduct auctionProduct) {
        auctionProductRepository.delete(auctionProduct);
    }
}
