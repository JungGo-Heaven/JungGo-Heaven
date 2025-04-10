package com.example.junggoheaven.domain.review.dto.response;

import com.example.junggoheaven.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponseDto {
    private final Long id;
    private final Byte rating;
    private final String comment;
    private final Long reviewerId;
    private final String reviewerName;
    private final LocalDateTime modifiedAt;

    public static ReviewResponseDto of(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewer().getId(),
                review.getReviewer().getName(),
                review.getModifiedAt()
        );
    }
}
