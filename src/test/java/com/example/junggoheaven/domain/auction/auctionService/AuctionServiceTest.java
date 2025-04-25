package com.example.junggoheaven.domain.auction.auctionService;

import com.example.junggoheaven.domain.auction.dto.request.AuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.request.UpdateAuctionRequestDto;
import com.example.junggoheaven.domain.auction.dto.response.AuctionResponseDto;
import com.example.junggoheaven.domain.auction.dto.response.PagingAuctionResponseDto;
import com.example.junggoheaven.domain.auction.entity.Auction;
import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.exception.InvalidAuctionStartTimeException;
import com.example.junggoheaven.domain.auction.exception.NoPermissionToAuctionException;
import com.example.junggoheaven.domain.auction.service.auctionProductService.component.AuctionProductWriter;
import com.example.junggoheaven.domain.auction.service.auctionService.AuctionService;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionFinder;
import com.example.junggoheaven.domain.auction.service.auctionService.component.AuctionWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.redis.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTest {

    @InjectMocks
    private AuctionService auctionService;

    @Mock
    private AuctionFinder auctionFinder;

    @Mock
    private AuctionWriter auctionWriter;

    @Mock
    private AuctionProductWriter auctionProductWriter;

    @Mock
    private UserFinder userFinder;

    @Mock
    private RedisService redisService;

    private User seller;
    private AuctionRequestDto requestDto;
    private AuctionProduct auctionProduct;
    private Auction auction;


    @BeforeEach
    void setup() {
        seller = User.of("email@test.com", "tester", "01012345678");
        ReflectionTestUtils.setField(seller, "id", 1L);

        requestDto = new AuctionRequestDto(
                1L,
                100000,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(3),
                "productName",
                "description"
        );

        auctionProduct = AuctionProduct.of(seller, requestDto.getPrductName(), requestDto.getDescription());
        ReflectionTestUtils.setField(auctionProduct, "id", 1L);

        auction = Auction.of(auctionProduct, requestDto.getStartPrice(), requestDto.getStartTime(), requestDto.getEndTime());
        ReflectionTestUtils.setField(auction, "id", 1L);

    }

    @Test
    void 경매를_정상적으로_생성한다() {
        // given
        when(userFinder.findByUserId(anyLong())).thenReturn(seller);
        when(auctionProductWriter.save(any())).thenReturn(auctionProduct);
        when(auctionWriter.save(any())).thenReturn(auction);

        // when
        AuctionResponseDto response = auctionService.createAuction(1L, requestDto);

        // then
        assertThat(response.getDescription()).isEqualTo("description");
        verify(redisService).setAuctionTrigger(any());
        verify(auctionProductWriter).save(any());
        verify(auctionWriter).save(any());
    }

    @Test
    void 경매_시작시간이_과거이면_예외를_던진다() {
        // given
        requestDto = new AuctionRequestDto(
                1L,
                100000,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(3),
                "productName",
                "description"
        );
        when(userFinder.findByUserId(anyLong())).thenReturn(seller);
        when(auctionProductWriter.save(any())).thenReturn(auctionProduct);

        // when & then
        assertThatThrownBy(() -> auctionService.createAuction(1L, requestDto))
                .isInstanceOf(InvalidAuctionStartTimeException.class);
    }

    @Test
    void 경매를_정상적으로_수정한다() {
        // given
        UpdateAuctionRequestDto updateDto = new UpdateAuctionRequestDto(
                200000,
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(4)
        );

        when(userFinder.findByUserId(anyLong())).thenReturn(seller);
        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);

        // when
        auctionService.updateAuction(1L, 1L, updateDto);

        // then
        assertThat(auction.getStart_price()).isEqualTo(200000);
    }

    @Test
    void 타인이_경매를_수정하면_예외를_던진다() {
        // given
        User faker = User.of("fake@hack.com", "fake", "01000000000");
        ReflectionTestUtils.setField(faker, "id", 2L);

        when(userFinder.findByUserId(anyLong())).thenReturn(faker);
        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);

        UpdateAuctionRequestDto updateDto = new UpdateAuctionRequestDto(
                1234,
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(4)
        );

        // when & then
        assertThatThrownBy(() -> auctionService.updateAuction(2L, 1L, updateDto))
                .isInstanceOf(NoPermissionToAuctionException.class);
    }

    @Test
    void 경매를_정상적으로_삭제한다() {
        // given
        when(userFinder.findByUserId(anyLong())).thenReturn(seller);
        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);

        // when
        auctionService.deleteAuction(1L, 1L);

        // then
        verify(auctionWriter).delete(auction);
    }

    @Test
    void 타인이_경매를_삭제하면_예외가_발생한다() {
        // given
        User stranger = User.of("stranger@hack.com", "intruder", "01099999999");
        ReflectionTestUtils.setField(stranger, "id", 2L);

        when(userFinder.findByUserId(anyLong())).thenReturn(stranger);
        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);

        // when & then
        assertThatThrownBy(() -> auctionService.deleteAuction(2L, 1L))
                .isInstanceOf(NoPermissionToAuctionException.class);
    }

    @Test
    void 경매_리스트를_페이징_조회한다() {
        // given
        Page<Auction> auctionPage = new PageImpl<>(List.of(auction));
        when(auctionFinder.findPagingAuctions(any(Pageable.class))).thenReturn(auctionPage);

        // when
        Page<PagingAuctionResponseDto> result = auctionService.getAuctionList(PageRequest.of(0, 10));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(auctionFinder, times(1)).findPagingAuctions(any(Pageable.class));
    }

    @Test
    void 경매_단건을_조회한다() {
        // given
        when(auctionFinder.findAuctionById(anyLong())).thenReturn(auction);
        // when
        AuctionResponseDto result = auctionService.getAuction(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getAuctionId()).isEqualTo(auction.getId());
        verify(auctionFinder, times(1)).findAuctionById(1L);
    }
}
