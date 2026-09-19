package com.DB.SpringDB.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.DB.SpringDB.dto.ErrorDto;


@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(
            "USER_NOT_FOUND",ex.getMessage()
        ));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(
            "ORDER_NOT_FOUND", ex.getMessage()
        ));
    } 

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("PRODUCT_NOT_FOUND", ex.getMessage()));
    }   

    @ExceptionHandler(DuplicateProductNameException.class)
    public ResponseEntity<ErrorDto> handleDuplicateProductNameException(DuplicateProductNameException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("DUPLICATE_PRODUCT_NAME", ex.getMessage()));
    }   

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleInternalServerException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("INTERNAL_SERVER_ERROR", "Internal server error"));    
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HashMap<String , String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getCode(), error.getDefaultMessage());
        });
        StringBuilder errMsg = new StringBuilder();
        boolean isFirst = true;
        for(String field : fieldErrors.keySet()){
            if(!isFirst) {
                errMsg.append(", ");
            }
            isFirst = false;
            errMsg.append(field).append(" : ").append(fieldErrors.get(field));
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("INVALID_INPUT",errMsg.toString()));
    }
}