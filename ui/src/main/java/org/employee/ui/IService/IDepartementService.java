package org.employee.ui.IService;
 
import java.util.List;

import org.employee.ui.Payload.Request.DepartmentRequest;
import org.employee.ui.Payload.Response.DepartmentResponse;

public interface IDepartementService {
    void createDepartment(DepartmentRequest departmentRequest);
    void updateDepartment(String id , DepartmentRequest departmentRequest);
    void deleteDepartment(String id);
    DepartmentResponse getDepartment(String id);
    List<DepartmentResponse> getDepartments(int page , int size, String sortBy, String direction);
}