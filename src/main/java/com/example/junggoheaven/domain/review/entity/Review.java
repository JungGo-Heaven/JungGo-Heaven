package com.example.junggoheaven.domain.review.entity;

import com.example.junggoheaven.domain.review.dto.request.ReviewRequestDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    private Byte rating;

    private String comment;

    private LocalDateTime deleted_at;

    public Review(User user, User reviewer, ReviewRequestDto requestDto){
        this.user = user;
        this.reviewer = reviewer;
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }

    public void updateReview(Byte rating, String comment){
        this.rating = rating;
        this.comment = comment;
    }

    public void deleteReview() {
        this.deleted_at = LocalDateTime.now();
    }
}
