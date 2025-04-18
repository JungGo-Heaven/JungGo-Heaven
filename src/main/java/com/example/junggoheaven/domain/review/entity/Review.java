package com.example.junggoheaven.domain.review.entity;

import com.example.junggoheaven.domain.review.dto.request.ReviewRequestDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Filter(name = "deletedFilter", condition = "status <> 'DELETED'")
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

    private Review(User user, User reviewer, ReviewRequestDto requestDto){
        this.user = user;
        this.reviewer = reviewer;
        this.rating = requestDto.getRating();
        this.comment = requestDto.getComment();
    }

    public void updateReview(Byte rating, String comment){
        this.rating = rating;
        this.comment = comment;
    }

    public static Review of(User user, User reviewer, ReviewRequestDto requestDto){
        return new Review(user, reviewer, requestDto);
    }

}
