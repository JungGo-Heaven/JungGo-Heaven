package com.example.junggoheaven.domain.review.service.component;

import com.example.junggoheaven.domain.review.entity.Review;
import com.example.junggoheaven.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewWriter {
    private final ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }
}
