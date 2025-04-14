package com.example.junggoheaven.domain.image.exception;

public class FileUploadLimitExceededException extends ImageException {
    public FileUploadLimitExceededException() {
        super(ImageErrorCode.FILE_UPLOAD_LIMIT_EXCEEDED);
    }
}
