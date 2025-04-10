package com.example.junggoheaven.domain.like.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.junggoheaven.domain.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {

	int countByProduct_Id(Long productId);

	@EntityGraph(attributePaths = {"product"})
	@Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.product.deletedAt IS NULL")
	Page<Like> findByUserAndValidProductOrderByIdAsc(Long userId, Pageable pageable);

	@EntityGraph(attributePaths = {"product"})
	@Query("SELECT l FROM Like l WHERE l.product.id = :productId AND l.product.deletedAt IS NULL")
	Like findByValidProductId(Long productId);

	@Query("SELECT l FROM Like l WHERE l.id = :likeId AND l.product.deletedAt IS NULL")
	Optional<Like> findValidById(Long likeId);
}
