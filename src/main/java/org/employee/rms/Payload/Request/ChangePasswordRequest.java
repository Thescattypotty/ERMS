package org.employee.rms.Payload.Request;

public record ChangePasswordRequest(
    String oldPassword,
    String newPassword
) {
    
}
