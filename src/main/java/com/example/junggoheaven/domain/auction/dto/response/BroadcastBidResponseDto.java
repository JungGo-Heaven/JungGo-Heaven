package com.example.junggoheaven.domain.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BroadcastBidResponseDto {
    private Long auctionId;
    private Integer winningPrice;
    private String bidderName;
    private LocalDateTime bidAt;
}
