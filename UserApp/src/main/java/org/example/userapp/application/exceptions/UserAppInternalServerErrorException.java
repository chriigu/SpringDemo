package org.example.userapp.application.exceptions;

public class UserAppInternalServerErrorException extends UserAppException {
    public UserAppInternalServerErrorException(String message) {
        super(message);
    }

    public UserAppInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
