package com.example.junggoheaven.domain.image.exception.imageException;

import com.example.junggoheaven.global.common.exception.BaseException;

public class ImageException extends BaseException {
    public ImageException(ImageErrorCode errorCode) {
        super(errorCode);
    }
}
