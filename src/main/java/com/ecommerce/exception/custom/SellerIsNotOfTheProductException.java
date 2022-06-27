package com.ecommerce.exception.custom;


public class SellerIsNotOfTheProductException extends RuntimeException{
    public SellerIsNotOfTheProductException(String message){
        super(message);
    }
}
