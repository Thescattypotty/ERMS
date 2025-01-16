package org.employee.ui.Payload.Response;

public record UserResponse(
    String id, 
    String email,
    String roleName
) {
    
}
