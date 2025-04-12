package com.example.junggoheaven.domain.like.exception;

public class LikeDeletionException extends LikeException{
	public LikeDeletionException() {
		super(LikeErrorCode.LIKE_DELETION);
	}
}
