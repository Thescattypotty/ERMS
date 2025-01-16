package org.employee.rms.Exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PasswordIncorrectException", description = "Exception for incorrect password")
public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String message) {
        super(message);
    }
    
}
