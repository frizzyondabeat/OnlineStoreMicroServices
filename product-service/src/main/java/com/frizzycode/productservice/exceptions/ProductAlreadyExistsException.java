package com.frizzycode.productservice.exceptions;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException() {
        super();
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }

}
