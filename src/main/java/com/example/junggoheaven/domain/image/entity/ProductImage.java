package com.example.junggoheaven.domain.image.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_images")
public class ProductImage extends TimeStamp {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productImageUrl;

    @Column(nullable = false)
    private String keyName;

    private ProductImage(String profileImageUrl, String keyName) {
        this.productImageUrl = profileImageUrl;
        this.keyName = keyName;
    }

    public static ProductImage of(String productImageUrl, String keyName) {
        return new ProductImage(productImageUrl, keyName);
    }
}
