package com.challenges.price_finder.config;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.challenges.price_finder.domain.exception.PriceNotFoundException;

import jakarta.validation.ConstraintViolationException;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handlePriceNotFoundException() {
        PriceNotFoundException exception = new PriceNotFoundException("Price not found");
        ResponseEntity<?> response = exceptionHandler.handlePriceNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleConstraintViolationException() {
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", new HashSet<>());
        ResponseEntity<?> response = exceptionHandler.handleConstraintViolationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                null, null, "paramName", null, new RuntimeException());
        ResponseEntity<?> response = exceptionHandler.handleMethodArgumentTypeMismatchException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleMissingServletRequestParameterException() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("paramName", "paramType");
        ResponseEntity<?> response = exceptionHandler.handleMissingServletRequestParameterException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleException() {
        Exception exception = new RuntimeException("Unexpected error");
        ResponseEntity<?> response = exceptionHandler.handleException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}