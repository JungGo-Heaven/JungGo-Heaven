package com.example.junggoheaven.domain.review.excetion;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ReviewNotFoundException extends BaseException {
    public ReviewNotFoundException() {
        super(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
}
