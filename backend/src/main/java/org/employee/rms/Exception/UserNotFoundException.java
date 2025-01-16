package org.employee.rms.Exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserNotFoundException", description = "Exception for user not found")
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
