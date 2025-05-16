package org.example.userapp.application.exceptions;

public class UserAppBadRequestException extends UserAppException {

    public UserAppBadRequestException(String message) {
        super(message);
    }

    public UserAppBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
