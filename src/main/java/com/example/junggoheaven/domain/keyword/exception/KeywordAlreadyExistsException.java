package com.example.junggoheaven.domain.keyword.exception;

public class KeywordAlreadyExistsException extends KeywordException{
	public KeywordAlreadyExistsException() {
		super(KeywordErrorCode.KEYWORD_ALREADY_EXISTS);
	}
}
