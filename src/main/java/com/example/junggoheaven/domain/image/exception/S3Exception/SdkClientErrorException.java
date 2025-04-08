package com.example.junggoheaven.domain.image.exception.S3Exception;

public class SdkClientErrorException extends S3Exception {
    public SdkClientErrorException() {
        super(S3ErrorCode.SDK_CLIENT_ERROR);
    }
}
