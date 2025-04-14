package com.example.junggoheaven.domain.review.excetion;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ReviewForbiddenException extends BaseException {
    public ReviewForbiddenException() {
        super(ReviewErrorCode.REVIEW_FORBIDDEN);
    }
}
