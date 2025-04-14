package com.example.junggoheaven.domain.image.exception;

public class UnexpectedErrorException extends ImageException {
    public UnexpectedErrorException() {
        super(ImageErrorCode.UNEXPECTED_ERROR);
    }
}
