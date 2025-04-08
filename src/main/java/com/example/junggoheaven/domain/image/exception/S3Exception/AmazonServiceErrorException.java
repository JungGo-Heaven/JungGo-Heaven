package com.example.junggoheaven.domain.image.exception.S3Exception;

public class AmazonServiceErrorException extends S3Exception {
    public AmazonServiceErrorException() {
        super(S3ErrorCode.AMAZON_SERVICE_ERROR);
    }
}
