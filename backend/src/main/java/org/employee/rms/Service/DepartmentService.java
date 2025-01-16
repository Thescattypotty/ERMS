package org.employee.rms.Service;

import java.util.UUID;

import org.employee.rms.Entity.Department;
import org.employee.rms.EntityRepository.DepartmentRepository;
import org.employee.rms.Exception.DepartmentNotFoundException;
import org.employee.rms.IService.IDepartmentService;
import org.employee.rms.Payload.Mapper.DepartmentMapper;
import org.employee.rms.Payload.Request.DepartmentRequest;
import org.employee.rms.Payload.Response.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService implements IDepartmentService{

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;


    @Override
    @Transactional
    public void createDepartment(DepartmentRequest departmentRequest) {
        departmentRepository.save(departmentMapper.toDepartment(departmentRequest));
    }

    @Override
    @Transactional
    public void updateDepartment(String id, DepartmentRequest departmentRequest) {
        if(!departmentRepository.existsById(UUID.fromString(id))){
            throw new DepartmentNotFoundException("Department not found");
        }
        Department department = departmentMapper.toDepartment(departmentRequest);
        department.setId(UUID.fromString(id));
        departmentRepository.save(department);
        
    }
    @Override
    @Transactional
    public void deleteDepartment(String id) {
        if(!departmentRepository.existsById(UUID.fromString(id))){
            throw new DepartmentNotFoundException("Department not found");
        }
        departmentRepository.deleteById(UUID.fromString(id));
    }
    @Override
    public DepartmentResponse getDepartment(String id) {
        return departmentRepository.findById(UUID.fromString(id))
            .map(departmentMapper::fromDepartment)
            .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
    }
    @Override
    public Page<DepartmentResponse> getAllDepartments(int page, int size, String sortBy, String direction) {
        log.info("Starting function departement");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        log.info("Pageable is initialized");
        return departmentRepository.findAll(pageable)
            .map(departmentMapper::fromDepartment);
    }
}
