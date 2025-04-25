package com.example.junggoheaven.domain.location.exception;

import com.example.junggoheaven.global.common.exception.BaseException;

public class LocationException extends BaseException {
    public LocationException(LocationErrorCode errorCode) {
        super(errorCode);
    }
}
