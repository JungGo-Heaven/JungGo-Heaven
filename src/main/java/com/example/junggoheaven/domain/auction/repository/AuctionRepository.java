package com.example.junggoheaven.domain.auction.repository;

import com.example.junggoheaven.domain.auction.entity.Auction;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @EntityGraph
    Page<Auction> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph
    Auction findAuctionById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Auction a WHERE a.id = :auctionId")
    Auction findAuctionByIdForBid(@Param("auctionId") Long id);
}
