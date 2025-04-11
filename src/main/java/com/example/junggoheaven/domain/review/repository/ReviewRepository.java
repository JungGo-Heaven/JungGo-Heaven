package com.example.junggoheaven.domain.review.repository;

import com.example.junggoheaven.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    @EntityGraph
    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT AVG(r.rating) FROM Review r WHERE r.user.id = :userId
            """)
    Double getRatingAverageByUserId(Long userId);
}
