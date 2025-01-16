package org.employee.rms.Payload.Request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "EmployeeRequest", description = "Request body required to create/update an employee")
public record EmployeeRequest(
    @Schema(description = "Full name of the employee", example = "John Doe", required = true)
    @NotNull(message = "Full name is required")
    @NotBlank(message = "Full name is required") 
    String fullName,

    @Schema(description = "Job title of the employee", example = "Software Engineer", required = true)
    @NotNull(message = "Job title is required")
    @NotBlank(message = "Job title is required")
    String jobTitle,

    @Schema(description = "Department ID of the employee", example = "UUID Exemple", required = true)
    @NotNull(message = "Department ID is required")
    @NotBlank(message = "Department ID is required")
    String departmentId,

    @Schema(description = "Hire date of the employee", example = "2021-01-01", required = true)
    @NotNull(message = "Hire date is required")
    LocalDate hireDate,

    @Schema(description = "Employment status of the employee", example = "Full-time", required = true)
    @NotNull(message = "Employment status is required")
    @NotBlank(message = "Employment status is required")
    String employmentStatus,

    @Schema(description = "Phone number of the employee", example = "1234567890", required = true)
    @NotNull(message = "Phone number is required")
    @Min(value = 10, message = "Phone number should be at least 10 digits")
    String phoneNumber,

    @Schema(description = "Email of the employee", example = "example@email.com", required = true)
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    String email,

    @Schema(description = "Address of the employee", example = "1234 Example St, City, Country", required = true)
    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    String address,

    @Schema(description = "User ID of the employee", example = "UUID Exemple", required = false)
    String userId
) {
    
}
