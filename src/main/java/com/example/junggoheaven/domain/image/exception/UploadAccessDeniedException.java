package com.example.junggoheaven.domain.image.exception;

public class UploadAccessDeniedException extends ImageException {
    public UploadAccessDeniedException() {
        super(ImageErrorCode.UPLOAD_ACCESS_DENIED);
    }
}
