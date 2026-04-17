package com.bluedigi.exam.customer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<String> handleOrderException(CustomerException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(ex.getMessage());
    }
}