package com.example.store.impl.logic.exceptions;

public class OrderBadRequestException extends RuntimeException {

    public OrderBadRequestException(String field) {
        super(String.format("Order bad request. %s", field), null);
    }
}