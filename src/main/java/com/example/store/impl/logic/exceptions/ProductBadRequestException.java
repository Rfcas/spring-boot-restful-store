package com.example.store.impl.logic.exceptions;

public class ProductBadRequestException extends RuntimeException {

    public ProductBadRequestException(String message) {
        super(String.format("Product bad request. %s", message), null);
    }
}