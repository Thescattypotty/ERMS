package org.employee.rms.Payload.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeRequest(
    @NotNull(message = "Full name is required")
    @NotBlank(message = "Full name is required") 
    String fullName,

    @NotNull(message = "Job title is required")
    @NotBlank(message = "Job title is required")
    String jobTitle,

    @NotNull(message = "Department ID is required")
    @NotBlank(message = "Department ID is required")
    String departmentId,

    @NotNull(message = "Hire date is required")
    LocalDate hireDate,

    @NotNull(message = "Employment status is required")
    @NotBlank(message = "Employment status is required")
    String employmentStatus,

    @NotNull(message = "Phone number is required")
    @Min(value = 10, message = "Phone number should be at least 10 digits")
    String phoneNumber,

    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    String email,

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    String address,

    String userId
) {
    
}
