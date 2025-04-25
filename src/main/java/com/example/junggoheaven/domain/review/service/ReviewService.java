package com.example.junggoheaven.domain.review.service;

import com.example.junggoheaven.domain.review.dto.request.ReviewRequestDto;
import com.example.junggoheaven.domain.review.dto.response.ReviewRatingResponseDto;
import com.example.junggoheaven.domain.review.dto.response.ReviewResponseDto;
import com.example.junggoheaven.domain.review.entity.Review;
import com.example.junggoheaven.domain.review.excetion.ReviewForbiddenException;
import com.example.junggoheaven.domain.review.service.component.ReviewFinder;
import com.example.junggoheaven.domain.review.service.component.ReviewWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewFinder reviewFinder;
    private final ReviewWriter reviewWriter;

    private final UserFinder userFinder;

    @Transactional
    public void createReview(Long userId, Long reviewerId, ReviewRequestDto requestDto) {
        User user = userFinder.findByUserId(userId);
        User reviewer = userFinder.findByUserId(reviewerId);

        Review review = Review.of(user, reviewer, requestDto);
        reviewWriter.save(review);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Page<ReviewResponseDto>> getReviews(Long userId, Pageable pageable) {
        User user = userFinder.findByUserId(userId);
        Page<Review> reviews = reviewFinder.findReviewsByUserId(user.getId(), pageable);

        Page<ReviewResponseDto> response = reviews.map(ReviewResponseDto::of);

        return ResponseDto.success(response);
    }

    @Transactional
    public void updateReview(Long reviewId, Long userId, ReviewRequestDto requestDto) {
        Review review = reviewFinder.findByReviewId(reviewId);

        if(!review.getReviewer().getId().equals(userId)) {
            throw new ReviewForbiddenException();
        }

        review.updateReview(requestDto.getRating(), requestDto.getComment());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewFinder.findByReviewId(reviewId);

        if(!review.getReviewer().getId().equals(userId)) {
            throw new ReviewForbiddenException();
        }
        reviewWriter.delete(review);
    }

    @Transactional(readOnly = true)
    public ResponseDto<ReviewRatingResponseDto> getReviewRating(Long userId) {
        double averageRating = reviewFinder.getRatingAverageByUserId(userId);
        return ResponseDto.success(new ReviewRatingResponseDto(userId, averageRating));
    }
}
