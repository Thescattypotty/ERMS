package org.employee.rms.Controller;

import java.time.LocalDate;

import org.employee.rms.Payload.Request.EmployeeRequest;
import org.employee.rms.Payload.Response.EmployeeResponse;
import org.employee.rms.Service.EmployeeService;
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

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<Void> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
        employeeService.createEmployee(employeeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") String id,@RequestBody @Valid EmployeeRequest employeeRequest){
        employeeService.updateEmployee(id,employeeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") String id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("id") String id){
        return new ResponseEntity<>(employeeService.getEmployee(id),HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        @RequestParam(required = false) String departmentId,
        @RequestParam(required = false) LocalDate hireDate,
        @RequestParam(required = false) String employmentStatus
        ){
        return new ResponseEntity<>(employeeService.getEmployees(page, size, sortBy, direction, employmentStatus, departmentId, hireDate),HttpStatus.OK);
    }
}
