package com.example.junggoheaven.domain.review.service.component;

import com.example.junggoheaven.domain.review.entity.Review;
import com.example.junggoheaven.domain.review.excetion.ReviewNotFoundException;
import com.example.junggoheaven.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFinder {
    private final ReviewRepository reviewRepository;

    public Page<Review> findReviewsByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findReviewsByUserId(userId, pageable);
    }

    public Review findByReviewId(Long id) {
        return reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
    }

    public double getRatingAverageByUserId(Long userId) {
        Double ratingAverage = reviewRepository.getRatingAverageByUserId(userId);
        if(ratingAverage == null){
            return 0.0;
        }
        return ratingAverage;
    }
}
