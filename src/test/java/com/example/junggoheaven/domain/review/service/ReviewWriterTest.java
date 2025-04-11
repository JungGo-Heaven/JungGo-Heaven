package com.example.junggoheaven.domain.review.service;

import com.example.junggoheaven.domain.review.entity.Review;
import com.example.junggoheaven.domain.review.repository.ReviewRepository;
import com.example.junggoheaven.domain.review.service.component.ReviewWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewWriterTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewWriter reviewWriter;

    @Test
    void save_success() {
        Review review = mock(Review.class);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewWriter.save(review);

        assertNotNull(savedReview);
        verify(reviewRepository, times(1)).save(review);
    }
}
