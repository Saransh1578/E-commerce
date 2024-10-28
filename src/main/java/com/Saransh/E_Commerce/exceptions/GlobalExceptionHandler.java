package com.Saransh.E_Commerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    

    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex)
    {
        String message="You do not have permission to this action!";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
