package org.employee.ui.Payload.Request;

import java.time.LocalDate;


public record EmployeeRequest(
    String fullName,

    String jobTitle,

    String departmentId,

    LocalDate hireDate,

    String employmentStatus,

    String phoneNumber,

    String email,

    String address,

    String userId
) {
    
}
