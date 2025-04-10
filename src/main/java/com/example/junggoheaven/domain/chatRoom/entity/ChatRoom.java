package com.example.junggoheaven.domain.chatRoom.entity;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_room",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "buyer_id"}))
@Getter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(name = "buyer_exited")
    private Long buyerExited;

    @Column(name = "seller_exited")
    private Long sellerExited;



    public void buyerExited(Long id){
        this.buyerExited = id;
    }
    public void sellerExited(Long id){
        this.buyerExited = id;
    }

    public ChatRoom(Product product, User buyer) {
        this.product = product;
        this.buyer = buyer;
        this.buyerExited = null;
        this.sellerExited = null;
    }

}
