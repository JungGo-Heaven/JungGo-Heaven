package com.example.junggoheaven.domain.like.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import com.example.junggoheaven.global.common.exception.ErrorCode;

public class LikeException extends BaseException {
	public LikeException(LikeErrorCode errorCode) {
		super(errorCode);
	}
}
