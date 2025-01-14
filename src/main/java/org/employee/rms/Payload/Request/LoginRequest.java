package org.employee.rms.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @Email(message = "Please provide a valid email")
    String email,

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    String password
) {
    
}
