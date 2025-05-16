package org.example.userapp.application.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamValidatorTest {


    @Test
    void isBlank() {
        // given
        String input = "   ";
        // when
        boolean result = ParamValidator.isNotNullAndNotBlank(input);
        // then
        assertFalse(result);
    }

    @Test
    void isEmpty() {
        // given
        String input = "";
        // when
        boolean result = ParamValidator.isNotNullAndNotBlank(input);
        // then
        assertFalse(result);
    }

    @Test
    void isNull() {
        // given
        String input = null;
        // when
        boolean result = ParamValidator.isNotNullAndNotBlank(input);
        // then
        assertFalse(result);
    }

    @Test
    void isNotNullAndNotBlank() {
        // given
        String input = "Input";
        // when
        boolean result = ParamValidator.isNotNullAndNotBlank(input);
        // then
        assertTrue(result);
    }
}