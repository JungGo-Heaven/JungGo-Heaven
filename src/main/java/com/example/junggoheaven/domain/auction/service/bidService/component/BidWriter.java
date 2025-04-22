package com.example.junggoheaven.domain.auction.service.bidService.component;

import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.repository.BidRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidWriter {
    private final BidRespository bidRespository;

    public Bid save(Bid bid) {
        return bidRespository.save(bid);
    }
}
