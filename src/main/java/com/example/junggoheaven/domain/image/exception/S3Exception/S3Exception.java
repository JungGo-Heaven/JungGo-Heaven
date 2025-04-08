package com.example.junggoheaven.domain.image.exception.S3Exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class S3Exception extends BaseException {
    public S3Exception(S3ErrorCode errorCode) {
        super(errorCode);
    }
}
