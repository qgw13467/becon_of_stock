package com.ssafy.beconofstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        return new ResponseEntity<>("정보를 찾을 수 없습니다.", HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e){
        return new ResponseEntity<>("정보를 찾을 수 없습니다",HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(Exception e){
        return new ResponseEntity<>("메소드가 허용되지 않았습니다",HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotYourAuthorizationException.class)
    public ResponseEntity<?> handleNotYourAuthorizationException(Exception e){
        return new ResponseEntity<>("NotYourAuthorizationException",HttpStatus.UNAUTHORIZED);
    }
}
