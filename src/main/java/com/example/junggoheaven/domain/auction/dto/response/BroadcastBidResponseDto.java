package com.example.junggoheaven.domain.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastBidResponseDto {
    private Long auctionId;
    private Integer winningPrice;
    private String bidderName;
    private LocalDateTime bidAt;
}
