package com.example.junggoheaven.domain.auction.dto.response;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EnterBidResponseDto {
    private Long auctionId;
    private String product_name;
    private int startPrice;
    private LocalDateTime endTime;
    private Long bidId;
    private String bidderName;

    public static EnterBidResponseDto of(Auction auction, @Nullable Bid bid) {
        return new EnterBidResponseDto(
                auction.getId(),
                auction.getAuctionProduct().getProduct_name(),
                auction.getStart_price(),
                auction.getEnd_time(),
                bid != null ? bid.getId() : null,
                bid != null ? bid.getBidder().getName() : null
        );
    }
}
