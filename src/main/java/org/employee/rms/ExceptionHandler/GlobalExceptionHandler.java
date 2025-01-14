package org.employee.rms.ExceptionHandler;

import org.employee.rms.Exception.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public String handleException(UserNotFoundException e){
        return e.getMessage();
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String handleException(BadCredentialsException e){
        return e.getMessage();
    }
}
