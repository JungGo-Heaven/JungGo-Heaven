package com.example.junggoheaven.domain.image.exception;

public class TypeMismatchException extends ImageException {
    public TypeMismatchException() {
        super(ImageErrorCode.TYPE_MISMATCH);
    }
}
