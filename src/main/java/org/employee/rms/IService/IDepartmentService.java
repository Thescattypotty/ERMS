package org.employee.rms.IService;

import org.employee.rms.Payload.Request.DepartmentRequest;
import org.employee.rms.Payload.Response.DepartmentResponse;
import org.springframework.data.domain.Page;

public interface IDepartmentService {
    void createDepartment(DepartmentRequest departmentRequest);
    void updateDepartment(String id , DepartmentRequest departmentRequest);
    void deleteDepartment(String id);
    DepartmentResponse getDepartment(String id);
    Page<DepartmentResponse> getAllDepartments(int page, int size, String sortBy, String direction);
}
