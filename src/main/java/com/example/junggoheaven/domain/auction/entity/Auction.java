package com.example.junggoheaven.domain.auction.entity;

import com.example.junggoheaven.domain.auction.enums.AuctionStatus;
import com.example.junggoheaven.domain.auction.dto.request.UpdateAuctionRequestDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auction_product_id")
    private AuctionProduct auctionProduct;

    @OneToOne
    @JoinColumn(name = "winner_id")
    @Nullable
    private User winner;

    private Integer start_price;

    private LocalDateTime start_time;

    private LocalDateTime end_time;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private Auction(AuctionProduct auctionProduct, Integer start_price, LocalDateTime start_time, LocalDateTime end_time) {
        this.auctionProduct = auctionProduct;
        this.start_price = start_price;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = AuctionStatus.WAITING;
    }
    public static Auction of(AuctionProduct auctionProduct, Integer start_price, LocalDateTime start_time, LocalDateTime end_time) {
        return new Auction(auctionProduct, start_price, start_time, end_time);
    }

    public void updateWinner(User winner) {
        this.winner = winner;
    }

    public void updateAuction(UpdateAuctionRequestDto requestDto) {
        this.start_price = requestDto.getStartPrice();
        this.start_time = requestDto.getStartTime();
        this.end_time = requestDto.getEndTime();
    }

    public void startAuction() {
        this.status = AuctionStatus.ONGOING;
    }

    public void failAuction() {
        this.status = AuctionStatus.FAILED;
    }
    public void successfulAuction() {
        this.status = AuctionStatus.SUCCESSFUL;
    }


}
