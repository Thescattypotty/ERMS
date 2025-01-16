package org.employee.rms.Payload.Response;

public record UserResponse(
    String id, 
    String email,
    String roleName
) {
    
}
