package org.employee.rms.Controller;

import org.employee.rms.Payload.Request.DepartmentRequest;
import org.employee.rms.Payload.Response.DepartmentResponse;
import org.employee.rms.Service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> createDepartment(@RequestBody @Valid DepartmentRequest departmentRequest) {
        departmentService.createDepartment(departmentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<Page<DepartmentResponse>> getAllDepartments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
        ) {
        log.info("Controller is Initialized");
        Page<DepartmentResponse> departmentResponses = departmentService.getAllDepartments(page, size, sortBy, direction);
        log.info("Controller is returning with content of departmets : {}", departmentResponses);
        return new ResponseEntity<>(departmentResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> updateDepartment(@PathVariable("id") String id, @RequestBody @Valid DepartmentRequest departmentRequest) {
        departmentService.updateDepartment(id, departmentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMINISTRATOR')")
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable("id") String id) {
        return new ResponseEntity<>(departmentService.getDepartment(id), HttpStatus.OK);
    }
    
}
