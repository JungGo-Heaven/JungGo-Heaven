package com.example.junggoheaven.domain.auction.controller;

import com.example.junggoheaven.domain.auction.dto.response.EnterBidResponseDto;
import com.example.junggoheaven.domain.auction.service.bidService.BidService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import com.example.junggoheaven.domain.auction.rabbitMQ.dto.BidMessage;
import com.example.junggoheaven.domain.auction.rabbitMQ.BidProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions/{auctionId}/bid")
public class BidController {
    private final BidService bidService;
    private final BidProducer bidProducer;

    @GetMapping
    public ResponseDto<EnterBidResponseDto> enterBid(
            @PathVariable Long auctionId
    ){
        return ResponseDto.success(bidService.enterBid(auctionId));
    }

    //데이터베이스(비관적락)로만 적용한 입찰하기 기능
//    @PostMapping
//    public ResponseDto<Void> createBid(
//            @AuthenticationPrincipal AuthUser authUser,
//            @PathVariable Long auctionId,
//            @RequestBody Integer bidPrice
//            ) {
//        bidService.createBid(authUser.getId(), auctionId, bidPrice);
//        return ResponseDto.success(null);
//    }

    @PostMapping
    public ResponseDto<Void> createBid(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long auctionId,
            @RequestBody Integer bidPrice
    ) {
        BidMessage message = new BidMessage(auctionId, authUser.getId(), bidPrice);
        bidProducer.sendBid(message);
        return ResponseDto.success(null);
    }
}
