package org.employee.rms.Exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EmployeeNotFoundException", description = "Exception for employee not found")
public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String message){
        super(message);
    }
    
}
