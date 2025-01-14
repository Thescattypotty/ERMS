package org.employee.rms.Service;

import java.time.LocalDate;
import java.util.UUID;

import org.employee.rms.Entity.Department;
import org.employee.rms.Entity.Employee;
import org.employee.rms.Entity.User;
import org.employee.rms.EntityRepository.DepartmentRepository;
import org.employee.rms.EntityRepository.EmployeeRepository;
import org.employee.rms.EntityRepository.UserRepository;
import org.employee.rms.Exception.DepartmentNotFoundException;
import org.employee.rms.Exception.EmployeeNotFoundException;
import org.employee.rms.Exception.UserNotFoundException;
import org.employee.rms.IService.IEmployeeService;
import org.employee.rms.Payload.Mapper.EmployeeMapper;
import org.employee.rms.Payload.Request.EmployeeRequest;
import org.employee.rms.Payload.Response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public void createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEmployee(employeeRequest);
        if(employeeRequest.userId() != null && !employeeRequest.userId().isEmpty()){
            User user = userRepository.findById(UUID.fromString(employeeRequest.userId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
            employee.setUser(user);
        }
        if(employeeRequest.departmentId() != null && !employeeRequest.departmentId().isEmpty()){
            Department department = departmentRepository.findById(UUID.fromString(employeeRequest.departmentId()))
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
            employee.setDepartment(department);
        }
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(String id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        employee.setFullName(employeeRequest.fullName());
        employee.setJobTitle(employeeRequest.jobTitle());
        employee.setHireDate(employeeRequest.hireDate());
        employee.setEmploymentStatus(employeeRequest.employmentStatus());
        employee.setPhoneNumber(employeeRequest.phoneNumber());
        employee.setEmail(employeeRequest.email());
        employee.setAddress(employeeRequest.address());
        if(employeeRequest.userId() != null && !employeeRequest.userId().isEmpty() && !employeeRequest.userId().equals(employee.getUser().getId().toString())){
            User user = userRepository.findById(UUID.fromString(employeeRequest.userId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
            employee.setUser(user);
        }
        if(employeeRequest.departmentId() != null && !employeeRequest.departmentId().isEmpty() && !employeeRequest.departmentId().equals(employee.getDepartment().getId().toString())){
            Department department = departmentRepository.findById(UUID.fromString(employeeRequest.departmentId()))
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
            employee.setDepartment(department);
        }
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(String id) {
        if(!employeeRepository.existsById(UUID.fromString(id))){
            throw new DepartmentNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public EmployeeResponse getEmployee(String id) {
        return employeeRepository.findById(UUID.fromString(id))
            .map(employeeMapper::fromEmployee)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public Page<EmployeeResponse> getEmployees(int page, int size, String sortBy, String direction,
            String employmentStatus, String departmentId, LocalDate hireDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        if(employmentStatus != null || hireDate != null){
            Department department = null;
            if(departmentId != null){
                department = departmentRepository.findById(UUID.fromString(departmentId))
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));   
            }
            return employeeRepository.searchEmployees(department, hireDate, employmentStatus, pageable)
                .map(employeeMapper::fromEmployee);
        }else{
            return employeeRepository.findAll(pageable)
                .map(employeeMapper::fromEmployee);
        }
    }

    
}
