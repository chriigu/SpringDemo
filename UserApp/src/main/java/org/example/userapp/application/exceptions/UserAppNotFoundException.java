package org.example.userapp.application.exceptions;

public class UserAppNotFoundException extends UserAppException {

    public UserAppNotFoundException(String message) {
        super(message);
    }

    public UserAppNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
