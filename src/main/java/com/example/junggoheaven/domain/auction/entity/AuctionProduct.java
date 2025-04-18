package com.example.junggoheaven.domain.auction.entity;

import com.example.junggoheaven.domain.auction.Enum.AuctionStatus;
import com.example.junggoheaven.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auction_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    private String product_name;
    private String description;

    private AuctionProduct(User seller, String product_name, String description){
        this.seller = seller;
        this.product_name = product_name;
        this.description = description;
    }
    public static AuctionProduct of(User seller, String product_name, String description){
        return new AuctionProduct(seller, product_name, description);
    }
}
