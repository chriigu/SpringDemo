package org.example.userapp.application.controller.rest;

import org.example.userapp.application.exceptions.UserAppBadRequestException;
import org.example.userapp.application.exceptions.UserAppInternalServerErrorException;
import org.example.userapp.application.exceptions.UserAppNotFoundException;
import org.example.userapp.application.exceptions.UserAppUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class UserAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAppBadRequestException.class)
    public ResponseEntity<Object> handleException(UserAppBadRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad request");

        return ResponseEntity.of(Optional.of(problemDetail));
    }

    @ExceptionHandler(UserAppNotFoundException.class)
    public ResponseEntity<Object> handleException(UserAppNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not found");

        return ResponseEntity.of(Optional.of(problemDetail));
    }

    @ExceptionHandler(UserAppUnauthorizedException.class)
    public ResponseEntity<Object> handleException(UserAppUnauthorizedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Unauthorized");

        return ResponseEntity.of(Optional.of(problemDetail));
    }

    @ExceptionHandler(UserAppInternalServerErrorException.class)
    public ResponseEntity<Object> handleException(UserAppInternalServerErrorException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("General error");

        return ResponseEntity.of(Optional.of(problemDetail));
    }
}
