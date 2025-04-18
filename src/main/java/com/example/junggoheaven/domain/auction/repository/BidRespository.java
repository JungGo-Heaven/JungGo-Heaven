package com.example.junggoheaven.domain.auction.repository;

import com.example.junggoheaven.domain.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRespository extends JpaRepository<Bid, Long> {
}
