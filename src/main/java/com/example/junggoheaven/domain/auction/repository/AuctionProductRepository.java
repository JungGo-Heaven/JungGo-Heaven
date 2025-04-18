package com.example.junggoheaven.domain.auction.repository;

import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionProductRepository extends JpaRepository<AuctionProduct, Long> {

}
