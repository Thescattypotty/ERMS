package org.employee.rms.Payload.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "LoginRequest", description = "The login request payload")
public record LoginRequest(
    @Schema(description = "The email of the user", required = true)
    @Email(message = "Please provide a valid email")
    String email,

    @Schema(description = "The password of the user", required = true)
    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    String password
) {
    
}
