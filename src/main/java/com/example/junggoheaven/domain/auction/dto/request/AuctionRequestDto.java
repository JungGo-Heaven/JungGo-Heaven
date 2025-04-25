package com.example.junggoheaven.domain.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuctionRequestDto {
    private Long productId;
    private int startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // AuctionProduct
    private String prductName;
    private String description;
}
