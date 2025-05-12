package org.example.userapp.application.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class UserAppNotFoundException extends UserAppException {

    public UserAppNotFoundException(String message) {
        super(message);
    }
}
