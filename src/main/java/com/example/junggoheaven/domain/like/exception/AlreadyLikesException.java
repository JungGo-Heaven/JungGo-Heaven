package com.example.junggoheaven.domain.like.exception;

public class AlreadyLikesException extends LikeException{
	public AlreadyLikesException(){
		super(LikeErrorCode.ALREADY_LIKES);
	}
}
