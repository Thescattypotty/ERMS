package org.employee.ui.Payload.Request;

public record LoginRequest(
    String email,

    String password
) {
    
}
