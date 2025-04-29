package com.example.junggoheaven.domain.auction.bidService;

import com.example.junggoheaven.domain.auction.dto.response.EnterBidResponseDto;
import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.entity.Bid;
import com.example.junggoheaven.domain.auction.enums.AuctionStatus;
import com.example.junggoheaven.domain.auction.exception.InvalidAuctionStatusException;
import com.example.junggoheaven.domain.auction.redis.RedisBidPublisher;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.bidService.BidService;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidFinder;
import com.example.junggoheaven.domain.auction.service.bidService.component.BidWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {
    @Mock
    private AuctionFinder auctionFinder;
    @Mock
    private UserFinder userFinder;
    @Mock
    private BidWriter bidWriter;
    @Mock
    private BidFinder bidFinder;
    @Mock
    private RedisBidPublisher redisBidPublisher;

    @InjectMocks
    private BidService bidService;

    private Auction auction;
    private User bidder;
    private Bid bid;
    private AuctionProduct auctionProduct;

    @BeforeEach
    void setUp() {
        bidder = User.of("test@email.com", "testUser", "01011112222");
        ReflectionTestUtils.setField(bidder, "id", 1L);

        auctionProduct = auctionProduct.of(bidder, "test", "test");
        ReflectionTestUtils.setField(auctionProduct, "id", 1L);


        auction = Auction.of(auctionProduct, 10000, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        ReflectionTestUtils.setField(auction, "id", 1L);

        bid = Bid.of(auction, bidder, 11000);
        ReflectionTestUtils.setField(bid, "id", 1L);
    }

    @Test
    void 입장시_가장_높은_입찰가가_있으면_가져온다() {
        auction.startAuction();

        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);
        when(bidFinder.findTopByAuctionOrderByBidPriceDesc(any(Auction.class))).thenReturn(Optional.of(bid));

        EnterBidResponseDto result = bidService.enterBid(auction.getId());

        assertThat(result.getBidPrice()).isEqualTo(bid.getBidPrice());
    }

    @Test
    void 경매상태가_ONGOING이_아니면_예외발생() {

        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);

        assertThatThrownBy(() -> bidService.enterBid(1L))
                .isInstanceOf(InvalidAuctionStatusException.class);
    }

    @Test
    void 입찰을_정상적으로_저장한다() {
        auction.startAuction();

        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);
        when(userFinder.findByUserId(anyLong())).thenReturn(bidder);
        when(bidWriter.save(any(Bid.class))).thenReturn(bid);

        bidService.createBid(1L, 1L, 12000);

        verify(bidWriter, times(1)).save(any(Bid.class));
        verify(redisBidPublisher, times(1)).publish(eq("auction.broadcast"), any());
    }

}
