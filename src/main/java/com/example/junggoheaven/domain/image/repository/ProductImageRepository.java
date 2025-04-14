package com.example.junggoheaven.domain.image.repository;

import com.example.junggoheaven.domain.image.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
