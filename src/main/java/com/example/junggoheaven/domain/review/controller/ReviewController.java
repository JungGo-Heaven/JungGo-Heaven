package com.example.junggoheaven.domain.review.controller;

import com.example.junggoheaven.domain.review.dto.request.ReviewRequestDto;
import com.example.junggoheaven.domain.review.dto.response.ReviewRatingResponseDto;
import com.example.junggoheaven.domain.review.dto.response.ReviewResponseDto;
import com.example.junggoheaven.domain.review.service.ReviewService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public void createReview(@PathVariable Long userId,
                             @AuthenticationPrincipal AuthUser authUser,
                             @RequestBody ReviewRequestDto requestDto) {
        reviewService.createReview(userId, authUser.getId(), requestDto);
    }

    @GetMapping("/reviews")
    public ResponseDto<Page<ReviewResponseDto>> getReviews(@PathVariable Long userId,
                                                                          Pageable pageable) {
        return reviewService.getReviews(userId, pageable);
    }

    @PatchMapping("/reviews/{reviewId}/update")
    public void updateReview(@PathVariable Long reviewId,
                             @AuthenticationPrincipal AuthUser authUser,
                             @RequestBody ReviewRequestDto requestDto) {
        reviewService.updateReview(reviewId, authUser.getId(), requestDto);
    }

    @PatchMapping("/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId,
                             @AuthenticationPrincipal AuthUser authUser) {
        reviewService.deleteReview(reviewId, authUser.getId());
    }

    @GetMapping("/rating")
    public ResponseDto<ReviewRatingResponseDto> getAverageRating(@PathVariable Long userId){
        return reviewService.getReviewRating(userId);
    }
}
