package com.example.itemservice.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException (BusinessException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getErrorCode()), HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}