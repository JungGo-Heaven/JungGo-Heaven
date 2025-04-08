package com.example.junggoheaven.domain.image.exception.S3Exception;

public class UnexpectedErrorException extends S3Exception {
    public UnexpectedErrorException() {
        super(S3ErrorCode.UNEXPECTED_ERROR);
    }
}
