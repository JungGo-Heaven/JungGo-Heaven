package com.example.junggoheaven.domain.auction.service.auctionService;

import com.example.junggoheaven.domain.auction.dto.request.AuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.request.UpdateAuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.response.AuctionResponseDto;
import com.example.junggoheaven.domain.auction.dto.response.PagingAuctionResponseDto;
import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.exception.InvalidAuctionStartTimeException;
import com.example.junggoheaven.domain.auction.exception.NoPermissionToAuctionException;
import com.example.junggoheaven.domain.auction.service.auctionProduct.component.AuctionProductWriter;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionFinder auctionFinder;
    private final AuctionWriter auctionWriter;

    private final AuctionProductWriter auctionProductWriter;

    private final UserFinder userFinder;

    @Transactional
    public AuctionResponseDto createAuction(Long userId, AuctionRequestDto requestDto) {
        User seller = userFinder.findByUserId(userId);
        AuctionProduct auctionProduct = AuctionProduct.of(seller, requestDto.getPrductName(), requestDto.getDescription());
        AuctionProduct savedAuctionProduct = auctionProductWriter.save(auctionProduct);
        if(requestDto.getStartTime().isBefore(LocalDateTime.now())){
            throw new InvalidAuctionStartTimeException();
        }
        Auction auction = Auction.of(
                savedAuctionProduct,
                requestDto.getStartPrice(),
                requestDto.getStartTime(),
                requestDto.getEndTime()
        );
        Auction savedAuction = auctionWriter.save(auction);

        return AuctionResponseDto.from(savedAuction, savedAuctionProduct);
    }
    @Transactional(readOnly = true)
    public Page<PagingAuctionResponseDto> getAuctionList(Pageable pageable) {
        Page<Auction> auctions = auctionFinder.findPagingAuctions(pageable);
        return auctions.map(PagingAuctionResponseDto::from);
    }

    @Transactional(readOnly = true)
    public AuctionResponseDto getAuction(Long auctionId) {
        Auction auction = auctionFinder.findAuctionById(auctionId);
        return AuctionResponseDto.from(auction, auction.getAuctionProduct());
    }

    @Transactional
    public void updateAuction(Long userId, Long auctionId, UpdateAuctionRequestDto requestDto) {
        User user = userFinder.findByUserId(userId);
        Auction auction = auctionFinder.findAuctionById(auctionId);

        if(!user.getId().equals(auction.getAuctionProduct().getSeller().getId())){
            throw new NoPermissionToAuctionException();
        }
        auction.updateAuction(requestDto);
    }

    @Transactional
    public void deleteAuction(Long userId, Long auctionId) {
        User user = userFinder.findByUserId(userId);
        Auction auction = auctionFinder.findAuctionById(auctionId);

        if(!user.getId().equals(auction.getAuctionProduct().getSeller().getId())){
            throw new NoPermissionToAuctionException();
        }
        auctionWriter.delete(auction);
    }

}