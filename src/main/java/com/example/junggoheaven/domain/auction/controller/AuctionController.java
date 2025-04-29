package com.example.junggoheaven.domain.auction.controller;

import com.example.junggoheaven.domain.auction.dto.request.AuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.request.UpdateAuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.response.AuctionResponseDto;
import com.example.junggoheaven.domain.auction.dto.response.PagingAuctionResponseDto;
import com.example.junggoheaven.domain.auction.service.auctionService.AuctionService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
public class AuctionController {
    private final AuctionService auctionService;

    @PostMapping
    public ResponseDto<AuctionResponseDto> createAuction (
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody AuctionRequestDto requestDto
    ) {
        return ResponseDto.success(auctionService.createAuction(authUser.getId(), requestDto));
    }

    @GetMapping
    public ResponseDto<Page<PagingAuctionResponseDto>> getAuctionList (
            Pageable pageable
    ) {
        return ResponseDto.success(auctionService.getAuctionList(pageable));
    }

    @GetMapping("/{auctionId}")
    public ResponseDto<AuctionResponseDto> getAuction (
            @PathVariable Long auctionId
    ) {
        return ResponseDto.success(auctionService.getAuction(auctionId));
    }

    @PatchMapping("/{auctionId}")
    public void updateAuction (
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long auctionId,
            @RequestBody UpdateAuctionRequestDto requestDto
            ) {
        auctionService.updateAuction(authUser.getId(), auctionId, requestDto);
    }

    @PutMapping("/{auctionId}")
    public void deleteAuction (
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long auctionId
    ) {
        auctionService.deleteAuction(authUser.getId(), auctionId);
    }

}
