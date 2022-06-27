package com.ecommerce.exception.custom;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
        super();
    }

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

    public ExpiredRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
