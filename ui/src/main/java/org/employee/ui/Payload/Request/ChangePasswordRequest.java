package org.employee.ui.Payload.Request;

public record ChangePasswordRequest(
    String oldPassword,
    String newPassword
) {
    
}
