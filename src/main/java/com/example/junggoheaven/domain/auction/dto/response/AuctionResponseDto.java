package com.example.junggoheaven.domain.auction.dto.response;

import com.example.junggoheaven.domain.auction.enums.AuctionStatus;
import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuctionResponseDto {
    private Long auctionId;
    private Long auctionProductId;
    private String auctionProductName;
    private String description;
    private int startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    private LocalDateTime createdAt;

    public static AuctionResponseDto from(Auction auction, AuctionProduct auctionProduct) {
        return new AuctionResponseDto(
                auction.getId(),
                auctionProduct.getId(),
                auctionProduct.getProduct_name(),
                auctionProduct.getDescription(),
                auction.getStart_price(),
                auction.getStart_time(),
                auction.getEnd_time(),
                auction.getStatus(),
                auction.getCreatedAt());
    }
}
