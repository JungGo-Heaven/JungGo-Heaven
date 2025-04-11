package com.example.junggoheaven.domain.review.dto.response;

import com.example.junggoheaven.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRatingResponseDto {
    private final Long userId;
    private final double averageRating;

    public static ReviewRatingResponseDto of(Long userId, double averageRating) {
        return new ReviewRatingResponseDto(userId, averageRating);
    }
}
