package org.employee.ui.Exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message){
        super(message);
    }
}
