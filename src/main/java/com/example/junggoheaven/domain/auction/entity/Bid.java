package com.example.junggoheaven.domain.auction.entity;

import com.example.junggoheaven.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    @Column(name = "bid_price")
    private Integer bidPrice;

    @Column(name = "bid_at")
    private LocalDateTime bidAt;

    private Bid(Auction auction, User bidder, Integer bid_price) {
        this.auction = auction;
        this.bidder = bidder;
        this.bidPrice = bid_price;
        this.bidAt = LocalDateTime.now();
    }

    public static Bid of(Auction auction, User bidder, Integer bid_price) {
        return new Bid(auction, bidder, bid_price);
    }
}
