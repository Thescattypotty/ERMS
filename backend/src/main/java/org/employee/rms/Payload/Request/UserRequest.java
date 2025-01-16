package org.employee.rms.Payload.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "UserRequest", description = "Request payload for creating a new user")
public record UserRequest(
    
    @Schema(description = "Email of the user", example = "example@gmail.com", required = true)
    @Email(message = "Email should be valid")
    String email,
    
    @Schema(description = "Password of the user", example = "password", required = true)
    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be blank")
    String password,

    @Schema(description = "Role of the user", example = "UUID Exemple", required = true)
    @NotNull(message = "Role id should not be null")
    String roleId
) {
    
}
