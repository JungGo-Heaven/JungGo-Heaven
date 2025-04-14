package com.example.junggoheaven.domain.image.exception;

public class InvalidUploadTypeException extends ImageException {
    public InvalidUploadTypeException() {
        super(ImageErrorCode.INVALID_UPLOAD_TYPE);
    }
}
