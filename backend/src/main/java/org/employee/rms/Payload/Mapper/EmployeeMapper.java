package org.employee.rms.Payload.Mapper;

import org.employee.rms.Entity.Employee;
import org.employee.rms.Payload.Request.EmployeeRequest;
import org.employee.rms.Payload.Response.EmployeeResponse;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {
    
    public Employee toEmployee(EmployeeRequest employeeRequest){
        return Employee.builder()
            .fullName(employeeRequest.fullName())
            .jobTitle(employeeRequest.jobTitle())
            .hireDate(employeeRequest.hireDate())
            .employmentStatus(employeeRequest.employmentStatus())
            .phoneNumber(employeeRequest.phoneNumber())
            .email(employeeRequest.email())
            .address(employeeRequest.address())
            .build();
    }

    public EmployeeResponse fromEmployee(Employee employee){
        return new EmployeeResponse(
            employee.getId().toString(),
            employee.getFullName(),
            employee.getJobTitle(),
            employee.getDepartment().getName(),
            employee.getHireDate(),
            employee.getEmploymentStatus(),
            employee.getPhoneNumber(),
            employee.getEmail(),
            employee.getAddress(),
            employee.getUser() != null ? employee.getUser().getId().toString() : ""
        );
    }
}
