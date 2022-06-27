package com.ecommerce.exception.custom;

public class RefreshTokenInvalidException extends RuntimeException{
    public RefreshTokenInvalidException(String message){
        super(message);
    }
}
