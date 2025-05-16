package org.example.userapp.application.exceptions;

public class UserAppUnauthorizedException extends UserAppException {
    public UserAppUnauthorizedException(String message) {
        super(message);
    }

    public UserAppUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
