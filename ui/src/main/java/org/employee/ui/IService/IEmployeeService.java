package org.employee.ui.IService;

import java.time.LocalDate;
import java.util.List;

import org.employee.ui.Payload.Request.EmployeeRequest;
import org.employee.ui.Payload.Response.EmployeeResponse;

public interface IEmployeeService {
    void createEmployee(EmployeeRequest employeeRequest);
    void updateEmployee(String id, EmployeeRequest employeeRequest);
    void deleteEmployee(String id);
    EmployeeResponse getEmployee(String id);
    List<EmployeeResponse> getEmployees(int page , int size, String sortBy, String direction, String departmentId,LocalDate hireDate, String employmentStatus);    
}
