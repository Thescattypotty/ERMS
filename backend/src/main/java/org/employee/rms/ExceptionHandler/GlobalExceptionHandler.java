package org.employee.rms.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

import org.employee.rms.Exception.DepartmentNotFoundException;
import org.employee.rms.Exception.EmployeeNotFoundException;
import org.employee.rms.Exception.PasswordIncorrectException;
import org.employee.rms.Exception.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestControllerAdvice
@Tag(name = "GlobalExceptionHandler", description = "Global Exception Handler")
public class GlobalExceptionHandler {
    
    @Operation(summary = "Handle Exception", description = "Handle UserNotFoundException, RoleNotFoundException, DepartmentNotFoundException, EmployeeNotFoundException")
    @ApiResponse(responseCode = "200", description = "Return Exception Message")
    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class, DepartmentNotFoundException.class, EmployeeNotFoundException.class})
    public String handleException(RuntimeException e){
        return e.getMessage();
    }
    @Operation(summary = "Handle Exception", description = "Handle BadCredentialsException")
    @ApiResponse(responseCode = "200", description = "Return Exception Message")
    @ExceptionHandler(BadCredentialsException.class)
    public String handleException(BadCredentialsException e){
        return e.getMessage();
    }

    @Operation(summary = "Handle Exception", description = "Handle PasswordIncorrectException")
    @ApiResponse(responseCode = "200", description = "Return Exception Message")
    @ExceptionHandler(PasswordIncorrectException.class)
    public String handleException(PasswordIncorrectException e){
        return e.getMessage();
    }

    @Operation(summary = "Handle Exception", description = "Handle Exception")
    @ApiResponse(responseCode = "200", description = "Return Exception Message")
    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e){
        return e.getMessage();
    }
}
