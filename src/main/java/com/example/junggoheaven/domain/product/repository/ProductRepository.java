package com.example.junggoheaven.domain.product.repository;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import java.util.Collection;
import java.util.List;

import com.example.junggoheaven.domain.product.entity.Product;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByIdIn(Collection<Long> ids);

	@Query("SELECT i FROM Product i WHERE i.id = :productId AND i.status <> 'DELETED'")
	Optional<Product> findByIdAndStatusIsNotDeleted(Long productId);

	@Query("SELECT p FROM Product p WHERE " +
			"ST_Distance_Sphere(p.location, :point) <= :radius " +
			"ORDER BY ST_Distance_Sphere(p.location, :point) ASC")
	Page<Product> findNearbyProductsByLocation(
			@Param("point") Point point,
			@Param("radius") double radius,
			Pageable pageable
	);
}
