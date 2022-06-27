package com.ecommerce.exception.custom;

public class UserNotFoundException extends RuntimeException{

   public UserNotFoundException(String message){
       super(message);
   }
}
