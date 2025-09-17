package org.employee.rms.Payload.Response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Standard error response structure")
public record ErrorResponse(
    @Schema(description = "Error message", example = "Employee not found")
    String message,
    
    @Schema(description = "HTTP status code", example = "404")
    int status,
    
    @Schema(description = "Timestamp when the error occurred")
    LocalDateTime timestamp,
    
    @Schema(description = "Request path that caused the error", example = "/employee/123")
    String path
) {
    public static ErrorResponse of(String message, int status, String path) {
        return new ErrorResponse(message, status, LocalDateTime.now(), path);
    }
    
    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(message, status, LocalDateTime.now(), null);
    }
}