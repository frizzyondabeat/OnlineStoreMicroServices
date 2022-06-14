package com.frizzycode.orderservice.exceptions;

public class OrderInvalidException extends RuntimeException {
    public OrderInvalidException() {
        super();
    }

    public OrderInvalidException(String message) {
        super(message);
    }
}
