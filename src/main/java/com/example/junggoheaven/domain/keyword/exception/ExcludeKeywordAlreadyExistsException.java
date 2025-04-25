package com.example.junggoheaven.domain.keyword.exception;

public class ExcludeKeywordAlreadyExistsException extends KeywordException{
	public ExcludeKeywordAlreadyExistsException() {
		super(KeywordErrorCode.EXCLUDE_KEYWORD_ALREADY_EXISTS);
	}
}
