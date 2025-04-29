package com.example.junggoheaven.domain.auction.bidService;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.repository.BidRespository;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidFinder;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidWriter;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidComponentTest {
    @Mock
    private BidRespository bidRepository;

    private BidFinder bidFinder;
    private BidWriter bidWriter;

    private Auction auction;
    private Bid bid;

    @BeforeEach
    void setUp() {
        bidFinder = new BidFinder(bidRepository);
        bidWriter = new BidWriter(bidRepository);

        auction = mock(Auction.class);
        User bidder = User.of("user@test.com", "tester", "01012345678");
        ReflectionTestUtils.setField(bidder, "id", 1L);

        bid = Bid.of(auction, bidder, 10000);
        ReflectionTestUtils.setField(bid, "id", 1L);
    }

    @Test
    void BidWriter_입찰_저장_성공() {
        when(bidRepository.save(any(Bid.class))).thenReturn(bid);

        Bid saved = bidWriter.save(bid);

        assertThat(saved).isNotNull();
        assertThat(saved.getBidPrice()).isEqualTo(10000);
        verify(bidRepository, times(1)).save(bid);
    }

    @Test
    void BidFinder_최고가_조회_성공() {
        when(bidRepository.findTopByAuctionOrderByBidPriceDesc(auction)).thenReturn(Optional.of(bid));

        Optional<Bid> found = bidFinder.findTopByAuctionOrderByBidPriceDesc(auction);

        assertThat(found).isPresent();
        assertThat(found.get().getBidPrice()).isEqualTo(10000);
        verify(bidRepository, times(1)).findTopByAuctionOrderByBidPriceDesc(auction);
    }
}
