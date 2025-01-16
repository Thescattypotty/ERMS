package org.employee.rms.Payload.Response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeResponse(
    @Schema(description = "Employee ID", example = "1")
    String id,
    @Schema(description = "Employee full name", example = "John Doe")
    String fullName,
    @Schema(description = "Employee job title", example = "Software Engineer")
    String jobTitle,
    @Schema(description = "Employee department name", example = "IT")
    String departmentName,
    @Schema(description = "Employee hire date", example = "2021-01-01")
    LocalDate hireDate,
    @Schema(description = "Employee employment status", example = "Active")
    String employmentStatus,
    @Schema(description = "Employee phone number", example = "1234567890")
    String phoneNumber,
    @Schema(description = "Employee email", example = "example@email.com", format = "email")
    String email,
    @Schema(description = "Employee address", example = "123 Main St, City, State, Zip")
    String address,
    @Schema(description = "Employee manager ID", example = "1")
    String userId
) {
    
}
