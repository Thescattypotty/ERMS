package org.employee.rms.Payload.Mapper;

import org.employee.rms.Entity.Department;
import org.employee.rms.Payload.Request.DepartmentRequest;
import org.employee.rms.Payload.Response.DepartmentResponse;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {
    
    public Department toDepartment(DepartmentRequest departmentRequest){
        return Department.builder()
            .name(departmentRequest.name())    
            .build();
    }

    public DepartmentResponse fromDepartment(Department department){
        return new DepartmentResponse(
            department.getId().toString(),
            department.getName()
        );
    }
}
