package com.example.junggoheaven.domain.auction.auctionService;

import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.repository.AuctionRepository;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionWriterTest {
    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionWriter auctionWriter;

    @Test
    void 경매_저장() {
        // given
        Auction auction = mock(Auction.class);
        when(auctionRepository.save(auction)).thenReturn(auction);

        // when
        Auction result = auctionWriter.save(auction);

        // then
        verify(auctionRepository, times(1)).save(auction);
        assertThat(result).isEqualTo(auction);
    }

    @Test
    void 경매_삭제() {
        // given
        Auction auction = mock(Auction.class);

        // when
        auctionWriter.delete(auction);

        // then
        verify(auctionRepository, times(1)).delete(auction);
    }
}
