package com.example.junggoheaven.domain.image.exception;

public class ImageUploadIOException extends ImageException {
    public ImageUploadIOException() {
        super(ImageErrorCode.IMAGE_UPLOAD_IO_EXCEPTION);
    }
}
