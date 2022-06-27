package com.ecommerce.exception.custom;

public class NotAmountAvailableException extends RuntimeException {

    public NotAmountAvailableException(String message) {
        super(message);
    }
}
