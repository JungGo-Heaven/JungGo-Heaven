package com.example.junggoheaven.domain.auction.auctionService;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.exception.AuctionNotFoundException;
import com.example.junggoheaven.domain.auction.repository.AuctionRepository;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionFinderTest {

    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionFinder auctionFinder;

    @Test
    void 경매_목록_페이징_조회한다() {
        // given
        Auction auction = mock(Auction.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Auction> page = new PageImpl<>(List.of(auction));

        when(auctionRepository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(page);

        // when
        Page<Auction> result = auctionFinder.findPagingAuctions(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(auctionRepository, times(1)).findAllByOrderByCreatedAtDesc(pageable);
    }

    @Test
    void 경매_ID로_정상조회한다() {
        // given
        Auction auction = mock(Auction.class);
        when(auctionRepository.findAuctionById(1L)).thenReturn(auction);

        // when
        Auction result = auctionFinder.findAuctionById(1L);

        // then
        assertThat(result).isNotNull();
        verify(auctionRepository, times(1)).findAuctionById(1L);
    }

    @Test
    void 경매_ID로_조회시_없으면_예외를_던진다() {
        // given
        when(auctionRepository.findAuctionById(1L)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> auctionFinder.findAuctionById(1L))
                .isInstanceOf(AuctionNotFoundException.class);
    }

    @Test
    void 비관적락_경매_정상조회() {
        // given
        Auction auction = mock(Auction.class);
        when(auctionRepository.findAuctionByIdForBid(1L)).thenReturn(auction);

        // when
        Auction result = auctionFinder.findAuctionByIdForBid(1L);

        // then
        assertThat(result).isNotNull();
        verify(auctionRepository).findAuctionByIdForBid(1L);
    }

    @Test
    void 비관적락_경매_조회시_없으면_예외를_던진다() {
        // given
        when(auctionRepository.findAuctionByIdForBid(1L)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> auctionFinder.findAuctionByIdForBid(1L))
                .isInstanceOf(AuctionNotFoundException.class);
    }
}

