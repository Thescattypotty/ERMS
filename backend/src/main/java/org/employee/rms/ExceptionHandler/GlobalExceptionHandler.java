package org.employee.rms.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

import org.employee.rms.Exception.DepartmentNotFoundException;
import org.employee.rms.Exception.EmployeeNotFoundException;
import org.employee.rms.Exception.PasswordIncorrectException;
import org.employee.rms.Exception.UserNotFoundException;
import org.employee.rms.Payload.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Tag(name = "GlobalExceptionHandler", description = "Global Exception Handler")
@Slf4j
public class GlobalExceptionHandler {
    
    @Operation(summary = "Handle Not Found Exceptions", description = "Handle UserNotFoundException, RoleNotFoundException, DepartmentNotFoundException, EmployeeNotFoundException")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class, DepartmentNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e, WebRequest request){
        log.error("Resource not found: {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(
            e.getMessage(), 
            HttpStatus.NOT_FOUND.value(), 
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @Operation(summary = "Handle Authentication Exception", description = "Handle BadCredentialsException")
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(BadCredentialsException e, WebRequest request){
        log.error("Authentication failed: {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(
            "Invalid credentials provided", 
            HttpStatus.UNAUTHORIZED.value(), 
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @Operation(summary = "Handle Password Exception", description = "Handle PasswordIncorrectException")
    @ApiResponse(responseCode = "400", description = "Password validation failed")
    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(PasswordIncorrectException e, WebRequest request){
        log.error("Password validation failed: {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(
            e.getMessage(), 
            HttpStatus.BAD_REQUEST.value(), 
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Operation(summary = "Handle General Exception", description = "Handle all other RuntimeExceptions")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(RuntimeException e, WebRequest request){
        log.error("Unexpected error occurred: {}", e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(
            "An unexpected error occurred. Please try again later.", 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
