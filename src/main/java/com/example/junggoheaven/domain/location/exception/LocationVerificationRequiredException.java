package com.example.junggoheaven.domain.location.exception;

public class LocationVerificationRequiredException extends LocationException {
    public LocationVerificationRequiredException() {
        super(LocationErrorCode.LOCATION_VERIFICATION_REQUIRED);
    }
}
