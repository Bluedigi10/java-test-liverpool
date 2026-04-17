package com.bluedigi.exam.customer.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bluedigi.exam.customer.dto.ErrorResponseDTO;

@RestControllerAdvice
public class CustomerExceptionHandler {
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderException(CustomerException ex) {
        
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(ex.getCode().value());
        error.setError(HttpStatus.valueOf(ex.getCode().value()).name());
        error.setMessage(ex.getMessage());

        return ResponseEntity
                .status(ex.getCode())
                .body(error);
    }
}