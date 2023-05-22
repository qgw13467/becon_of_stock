package com.ssafy.beconofstock.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("UserNotFoundException");
    }
    public NotFoundException(String msg){
        super(msg);
    }

}