package com.example.junggoheaven.domain.location.exception;

public class CoordinateConversionFailedException extends LocationException {
    public CoordinateConversionFailedException() {
        super(LocationErrorCode.COORDINATE_CONVERSION_FAILED);
    }
}
