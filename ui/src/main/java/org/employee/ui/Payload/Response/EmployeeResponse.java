package org.employee.ui.Payload.Response;

import java.time.LocalDate;

public record EmployeeResponse(
    String id,
    String fullName,
    String jobTitle,
    String departmentName,
    LocalDate hireDate,
    String employmentStatus,
    String phoneNumber,
    String email,
    String address,
    String userId
) {
    
}
