package org.employee.rms.Payload.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "DepartmentRequest", description = "Request payload to create/update a new department")
public record DepartmentRequest(
    @Schema(description = "Department name", example = "IT", required = true)
    @NotNull(message = "Department name is required")
    @NotBlank(message = "Department name is required")
    String name
) {
    
}
