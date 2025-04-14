package com.example.junggoheaven.domain.image.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_images")
public class ProductImage extends TimeStamp {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productImageUrl;

    @Column(nullable = false)
    private String keyName;

    public ProductImage(String profileImageUrl, String keyName) {
        this.productImageUrl = profileImageUrl;
        this.keyName = keyName;
    }
}
