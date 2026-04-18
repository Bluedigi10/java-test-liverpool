package com.bluedigi.exam.customer.exception;

import org.springframework.http.HttpStatusCode;

public class CustomerException extends RuntimeException {
    private final HttpStatusCode code;

    public CustomerException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
    }

    public HttpStatusCode getCode() {
        return code;
    }
}