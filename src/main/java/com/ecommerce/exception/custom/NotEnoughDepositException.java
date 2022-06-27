package com.ecommerce.exception.custom;

public class NotEnoughDepositException extends RuntimeException {
    public NotEnoughDepositException(String message) {
        super(message);
    }
}
