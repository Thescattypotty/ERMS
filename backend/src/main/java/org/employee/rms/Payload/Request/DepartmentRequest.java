package org.employee.rms.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequest(
    @NotNull(message = "Department name is required")
    @NotBlank(message = "Department name is required")
    String name
) {
    
}
