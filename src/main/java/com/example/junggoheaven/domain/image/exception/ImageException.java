package com.example.junggoheaven.domain.image.exception;

import com.example.junggoheaven.global.common.exception.BaseException;
import com.example.junggoheaven.global.common.exception.ErrorCode;

public class ImageException extends BaseException {
    public ImageException(ImageErrorCode errorCode) {
        super(errorCode);
    }
}
