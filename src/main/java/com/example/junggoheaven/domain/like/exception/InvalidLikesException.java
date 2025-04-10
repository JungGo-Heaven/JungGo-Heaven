package com.example.junggoheaven.domain.like.exception;

public class InvalidLikesException extends LikeException{
	public InvalidLikesException() {
		super(LikeErrorCode.INVALID_LIKES);
	}
}
