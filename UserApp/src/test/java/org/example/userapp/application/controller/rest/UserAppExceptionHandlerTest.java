package org.example.userapp.application.controller.rest;

import org.example.userapp.application.exceptions.UserAppBadRequestException;
import org.example.userapp.application.exceptions.UserAppInternalServerErrorException;
import org.example.userapp.application.exceptions.UserAppNotFoundException;
import org.example.userapp.application.exceptions.UserAppUnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserAppExceptionHandlerTest {

    private UserAppExceptionHandler handler = new UserAppExceptionHandler();

    @Test
    void handleExceptionUserAppBadRequestException() {
        // given
        // when
        ResponseEntity<Object> result = handler.handleException(new UserAppBadRequestException("abc"));
        // then
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("ProblemDetail[type='about:blank', title='Bad request', status=400, detail='abc', instance='null', properties='null']", result.getBody().toString());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void handleExceptionUserAppNotFoundException() {
        // given
        // when
        ResponseEntity<Object> result = handler.handleException(new UserAppNotFoundException("abc"));
        // then
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("ProblemDetail[type='about:blank', title='Not found', status=404, detail='abc', instance='null', properties='null']", result.getBody().toString());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void handleExceptionUserAppUnauthorizedException() {
        // given
        // when
        ResponseEntity<Object> result = handler.handleException(new UserAppUnauthorizedException("abc"));
        // then
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("ProblemDetail[type='about:blank', title='Unauthorized', status=401, detail='abc', instance='null', properties='null']", result.getBody().toString());
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    void handleExceptionUserAppInternalServerErrorException() {
        // given
        // when
        ResponseEntity<Object> result = handler.handleException(new UserAppInternalServerErrorException("abc"));
        // then
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("ProblemDetail[type='about:blank', title='General error', status=500, detail='abc', instance='null', properties='null']", result.getBody().toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}