package com.bluedigi.exam.order.exception;

import org.springframework.http.HttpStatusCode;

public class OrderException extends RuntimeException {
    private final HttpStatusCode code;

    public OrderException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
    }

    public HttpStatusCode getCode() {
        return code;
    }
}
