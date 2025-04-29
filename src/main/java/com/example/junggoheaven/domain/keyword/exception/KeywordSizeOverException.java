package com.example.junggoheaven.domain.keyword.exception;

public class KeywordSizeOverException extends KeywordException{
	public KeywordSizeOverException() {
		super(KeywordErrorCode.KEYWORD_SIZE_OVER);
	}
}
