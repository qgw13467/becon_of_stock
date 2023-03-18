package com.ssafy.beconofstock.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("UserNotFoundException");
    }
    public UserNotFoundException(String msg){
        super(msg);
    }
}
