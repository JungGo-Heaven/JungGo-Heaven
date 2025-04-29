package com.example.junggoheaven.domain.keyword.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import com.example.junggoheaven.global.common.exception.ErrorCode;

public class KeywordException extends BaseException {
	protected KeywordException(ErrorCode errorCode) {
		super(errorCode);
	}
}
