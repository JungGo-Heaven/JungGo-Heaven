package com.example.junggoheaven.domain.auction.rabbitMQ.dto;

public record BidMessage(Long auctionId, Long userId, Integer bidPrice) {}
