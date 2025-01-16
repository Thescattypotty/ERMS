package org.employee.rms.Payload.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DepartmentResponse", description = "Response payload for department")
public record DepartmentResponse(
    @Schema(description = "Department ID", example = "1")
    String id,
    @Schema(description = "Department name", example = "IT")
    String name
) {
    
}
