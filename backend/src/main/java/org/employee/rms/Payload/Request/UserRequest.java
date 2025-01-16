package org.employee.rms.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    
    @Email(message = "Email should be valid")
    String email,
    
    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be blank")
    String password,

    @NotNull(message = "Role id should not be null")
    String roleId
) {
    
}
