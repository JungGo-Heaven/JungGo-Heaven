package com.example.junggoheaven.domain.auction.repository;

import com.example.junggoheaven.domain.auction.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @EntityGraph
    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph
    Auction findAuctionById(Long id);
}
