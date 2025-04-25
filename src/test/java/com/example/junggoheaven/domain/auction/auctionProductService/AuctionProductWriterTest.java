package com.example.junggoheaven.domain.auction.auctionProductService;

import com.example.junggoheaven.domain.auction.entity.AuctionProduct;
import com.example.junggoheaven.domain.auction.repository.AuctionProductRepository;
import com.example.junggoheaven.domain.auction.service.auctionProductService.component.AuctionProductWriter;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionProductWriterTest {
    @Mock
    private AuctionProductRepository auctionProductRepository;

    @InjectMocks
    private AuctionProductWriter auctionProductWriter;

    @Test
    public void save(){
        User user = User.of("user@n.com", "user", "123456789");
        AuctionProduct auctionProduct =AuctionProduct.of(user, "asd", "dwq");

        when(auctionProductRepository.save(auctionProduct)).thenReturn(auctionProduct);

        // when
        AuctionProduct saved = auctionProductWriter.save(auctionProduct);

        // then
        assertEquals(auctionProduct, saved);
    }

    @Test
    public void delete() {
        // given
        User user = User.of("user@n.com", "user", "123456789");
        AuctionProduct auctionProduct = AuctionProduct.of(user, "title", "desc");

        // when
        auctionProductWriter.delete(auctionProduct);

        // then
        verify(auctionProductRepository).delete(auctionProduct);
    }

}
