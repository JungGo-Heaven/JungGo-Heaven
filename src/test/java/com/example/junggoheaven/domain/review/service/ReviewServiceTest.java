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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewFinder reviewFinder;

    @Mock
    private ReviewWriter reviewWriter;

    @Mock
    private UserFinder userFinder;

    @InjectMocks
    private ReviewService reviewService;


    private User user;
    private User reviewer;
    private ReviewRequestDto reviewRequestDto;
    private Review review;

    @BeforeEach
    void setUp() {
        user = User.of("user@test.com", "User", "123456789");
        ReflectionTestUtils.setField(user, "id", 1L);

        reviewer = User.of("reviewer@test.com", "Reviewer", "987654321");
        ReflectionTestUtils.setField(reviewer, "id", 2L);

        reviewRequestDto = new ReviewRequestDto((byte) 5, "친절하고 답변이 빠르십니다.");
        review = Review.of(user, reviewer, reviewRequestDto);
        ReflectionTestUtils.setField(review, "id", 1L);
    }

    @Test
    void createReview() {
        // given
        when(userFinder.findByUserId(1L)).thenReturn(user);
        when(userFinder.findByUserId(2L)).thenReturn(reviewer);
        when(reviewWriter.save(any(Review.class))).thenReturn(review);
        // when
        reviewService.createReview(1L, 2L, reviewRequestDto);
        // then
        verify(reviewWriter, times(1)).save(any(Review.class));
    }

    @Test
    void getReviews() {
        // given
        Pageable pageable = mock(Pageable.class);
        Page<Review> reviewsPage = new PageImpl<>(Arrays.asList(review));

        when(userFinder.findByUserId(1L)).thenReturn(user);
        when(reviewFinder.findReviewsByUserId(anyLong(), eq(pageable))).thenReturn(reviewsPage);

        // when
        ResponseDto<Page<ReviewResponseDto>> response = reviewService.getReviews(user.getId(), pageable);

        // then
        assertNotNull(response);
        assertEquals(1, response.getData().getContent().size());
    }

    @Test
    void updateReview_success() {
        // given
        reviewRequestDto = new ReviewRequestDto((byte) 4, "리뷰 수정하는 멘트입니다.");
        when(reviewFinder.findByReviewId(1L)).thenReturn(review);
        // when
        reviewService.updateReview(1L, 2L, reviewRequestDto);

        // then
        assertEquals((byte) 4, review.getRating());
        assertEquals("리뷰 수정하는 멘트입니다.", review.getComment());
    }

    @Test
    void updateReview_forbidden() {
        // given
        Long reviewId = 1L;
        Long userId = 3L;  // 실제 리뷰 작성자와 다른 userId

        when(reviewFinder.findByReviewId(1L)).thenReturn(review);

        // when & then
        assertThrows(ReviewForbiddenException.class, () -> reviewService.updateReview(reviewId, userId, reviewRequestDto));
    }

    @Test
    void deleteReview_success() {
        // given
        when(reviewFinder.findByReviewId(1L)).thenReturn(review);
        // when
        // then
        assertDoesNotThrow(() -> reviewService.deleteReview(1L, 2L));
    }

    @Test
    void deleteReview_forbidden() {
        // given
        Long userId = 3L;  // 리뷰 작성자와 다른 userId
        when(reviewFinder.findByReviewId(1L)).thenReturn(review);

        // when & then
        assertThrows(ReviewForbiddenException.class, () -> reviewService.deleteReview(review.getId(), userId));
    }

    @Test
    void getReviewRating() {
        // given
        double averageRating = 4.5;
        when(reviewFinder.getRatingAverageByUserId(1L)).thenReturn(averageRating);

        // when
        ResponseDto<ReviewRatingResponseDto> response = reviewService.getReviewRating(user.getId());

        // then
        assertNotNull(response);
        assertEquals(4.5, response.getData().getAverageRating());
    }
}
