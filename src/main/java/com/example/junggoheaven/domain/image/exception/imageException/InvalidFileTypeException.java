package com.example.junggoheaven.domain.image.exception.imageException;

public class InvalidFileTypeException extends ImageException {
    public InvalidFileTypeException() {
        super(ImageErrorCode.INVALID_FILE_TYPE);
    }
}
