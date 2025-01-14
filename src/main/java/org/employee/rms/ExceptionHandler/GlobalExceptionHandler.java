package org.employee.rms.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

import org.employee.rms.Exception.DepartmentNotFoundException;
import org.employee.rms.Exception.PasswordIncorrectException;
import org.employee.rms.Exception.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class, DepartmentNotFoundException.class})
    public String handleException(RuntimeException e){
        return e.getMessage();
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String handleException(BadCredentialsException e){
        return e.getMessage();
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public String handleException(PasswordIncorrectException e){
        return e.getMessage();
    }
}
