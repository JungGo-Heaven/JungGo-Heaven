package com.example.junggoheaven.domain.review.service;

import com.example.junggoheaven.domain.review.entity.Review;
import com.example.junggoheaven.domain.review.excetion.ReviewNotFoundException;
import com.example.junggoheaven.domain.review.repository.ReviewRepository;
import com.example.junggoheaven.domain.review.service.component.ReviewFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewFinderTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewFinder reviewFinder;

    private Review review;

    @BeforeEach
    void setUp() {
        review = mock(Review.class);
    }

    @Test
    void findReviewsByUserId_success() {
        Pageable pageable = mock(Pageable.class);
        when(reviewRepository.findReviewsByUserId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(review)));

        Page<Review> result = reviewFinder.findReviewsByUserId(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void findByReviewId_success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewFinder.findByReviewId(1L);

        assertNotNull(result);
    }

    @Test
    void findByReviewId_notFound() {
        when(reviewRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewFinder.findByReviewId(2L));
    }

    @Test
    void getRatingAverageByUserId_withValue() {
        when(reviewRepository.getRatingAverageByUserId(1L)).thenReturn(4.5);

        double result = reviewFinder.getRatingAverageByUserId(1L);

        assertEquals(4.5, result);
    }

    @Test
    void getRatingAverageByUserId_nullValue() {
        when(reviewRepository.getRatingAverageByUserId(2L)).thenReturn(null);

        double result = reviewFinder.getRatingAverageByUserId(2L);

        assertEquals(0.0, result);
    }
}
