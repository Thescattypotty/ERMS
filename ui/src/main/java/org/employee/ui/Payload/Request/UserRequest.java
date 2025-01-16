package org.employee.ui.Payload.Request;

public record UserRequest(
    
    String email,
    
    String password,

    String roleId
) {
    
}
