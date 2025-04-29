package com.example.junggoheaven.domain.bespokeinfo.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class BespokeinfoException extends BaseException {

	public BespokeinfoException(BespokeinfoErrorCode errorCode) {
		super(errorCode);
	}
}
