package com.virtusa.online_grocery_store.validatorerrorrepsonse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidatorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> erroes = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( (error)->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            erroes.put(fieldName,message);
        });
        return new ResponseEntity<>(erroes,HttpStatus.BAD_REQUEST);

    }
}
