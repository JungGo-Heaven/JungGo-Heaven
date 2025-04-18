package com.example.junggoheaven.domain.product.repository;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import java.util.Collection;
import java.util.List;

import com.example.junggoheaven.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByDeletedAtNull(Pageable pageable); // deleteAt이 null 인 즉 product 값이 존재하는경우

	List<Product> findByIdIn(Collection<Long> ids);

	@Query("SELECT i FROM Product i WHERE i.id = :productId AND i.status <> 'DELETED'")
	Optional<Product> findByIdAndStatusIsNotDeleted(Long productId);

	@Query("SELECT i FROM Product i WHERE i.status <> 'DELETED'")
	Page<Product> findAllAndStatusIsNotDeleted(Pageable pageable);
}
