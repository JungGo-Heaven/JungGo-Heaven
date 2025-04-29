package com.example.junggoheaven.domain.location.exception;

public class AddressResponseEmptyException extends LocationException {
    public AddressResponseEmptyException() {
        super(LocationErrorCode.ADDRESS_RESPONSE_EMPTY);
    }
}
