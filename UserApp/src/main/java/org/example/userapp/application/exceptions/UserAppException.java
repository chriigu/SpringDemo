package org.example.userapp.application.exceptions;

public class UserAppException extends RuntimeException {
    public UserAppException(String message) {
        super(message);
    }

    public UserAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
