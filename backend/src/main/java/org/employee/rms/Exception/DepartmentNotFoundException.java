package org.employee.rms.Exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DepartmentNotFoundException", description = "Exception for department not found")
public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(String message){
        super(message);
    }
    
}
