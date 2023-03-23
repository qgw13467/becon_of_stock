package com.ssafy.beconofstock.exception;

public class NotYourAuthorizationException extends RuntimeException{
    public NotYourAuthorizationException(){
        super("NotYourAuthorizationException");
    }
    public NotYourAuthorizationException(String msg){
        super(msg);
    }}
