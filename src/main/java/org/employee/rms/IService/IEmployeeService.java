package org.employee.rms.IService;

import java.time.LocalDate;

import org.employee.rms.Payload.Request.EmployeeRequest;
import org.employee.rms.Payload.Response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface IEmployeeService {
    void createEmployee(EmployeeRequest employeeRequest);
    void updateEmployee(String id, EmployeeRequest employeeRequest);
    void deleteEmployee(String id);
    EmployeeResponse getEmployee(String id);
    Page<EmployeeResponse> getEmployees(
        int page,
        int size,
        String sortBy,
        String direction,
        String employmentStatus,
        String departmentId,
        LocalDate hireDate
        );
}
