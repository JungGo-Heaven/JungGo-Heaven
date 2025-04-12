package com.example.junggoheaven.domain.product.repository;

import java.util.Collection;
import java.util.List;

import com.example.junggoheaven.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByDeletedAtNull(Pageable pageable); // deleteAt이 null 인 즉 product 값이 존재하는경우

	List<Product> findByIdIn(Collection<Long> ids);
}
