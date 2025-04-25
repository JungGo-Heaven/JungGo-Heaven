package com.example.junggoheaven.domain.location.exception;

public class NotInMyTownException extends LocationException {
    public NotInMyTownException() {
        super(LocationErrorCode.NOT_IN_MY_TOWN);
    }
}
