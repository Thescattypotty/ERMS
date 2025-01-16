package org.employee.rms.Payload.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ChangePasswordRequest", description = "Request payload to change password")
public record ChangePasswordRequest(
    @Schema(description = "Old password", example = "oldPassword", required = true)
    @NotNull
    @NotBlank
    String oldPassword,
    @Schema(description = "New password", example = "newPassword", required = true)
    @NotNull
    @NotBlank
    String newPassword
) {
    
}
