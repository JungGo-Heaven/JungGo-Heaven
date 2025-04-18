package com.example.junggoheaven.domain.auction.dto.response;

import com.example.junggoheaven.domain.auction.enums.AuctionStatus;
import com.example.junggoheaven.domain.auction.entity.Auction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PagingAuctionResponseDto {
    private Long auctionId;
    private String productName;
    private int startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;

    public static PagingAuctionResponseDto from(Auction auction) {
        return new PagingAuctionResponseDto(
                auction.getId(),
                auction.getAuctionProduct().getProduct_name(),
                auction.getStart_price(),
                auction.getStart_time(),
                auction.getEnd_time(),
                auction.getStatus()
        );
    }
}
