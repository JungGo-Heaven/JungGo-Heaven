package com.example.junggoheaven.domain.keyword.exception;

public class ExcludeKeywordSizeOverException extends KeywordException {
	public ExcludeKeywordSizeOverException() {
		super(KeywordErrorCode.EXCLUDE_KEYWORD_SIZE_OVER);
	}
}
