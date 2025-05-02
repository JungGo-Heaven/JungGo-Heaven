package com.example.junggoheaven.domain.bespokeinfo.exception;

public class SameAgreeException extends BespokeinfoException {

	public SameAgreeException() {
		super(BespokeinfoErrorCode.BESPOKE_AGREE_SAME);
	}
}
