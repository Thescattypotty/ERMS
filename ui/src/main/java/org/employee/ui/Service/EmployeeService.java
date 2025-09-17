package org.employee.ui.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.employee.ui.Exception.DepartementException;
import org.employee.ui.Exception.EmployeeException;
import org.employee.ui.IService.IEmployeeService;
import org.employee.ui.Payload.Request.EmployeeRequest;
import org.employee.ui.Payload.Response.EmployeeResponse;
import org.employee.ui.RestResponse.RestResponsePage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService implements IEmployeeService{
    private final RestClient restClient;

    @Override
    public void createEmployee(EmployeeRequest employeeRequest) {
        log.info("Creating employee with name: {}", employeeRequest.fullName());
        ResponseEntity<Void> response = restClient
            .post()
            .uri("/employee")
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .body(employeeRequest)
            .retrieve()
            .toEntity(Void.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            log.error("Failed to create employee. Status code: {}", response.getStatusCode());
            throw new EmployeeException("Failed to create employee. Status: " + response.getStatusCode());
        }
        log.info("Employee created successfully");
    }

    @Override
    public void updateEmployee(String id, EmployeeRequest employeeRequest) {
        log.info("Updating employee with ID: {} and name: {}", id, employeeRequest.fullName());
        ResponseEntity<Void> response = restClient
            .put()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .body(employeeRequest)
            .retrieve()
            .toEntity(Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to update employee with ID: {}. Status code: {}", id, response.getStatusCode());
            throw new EmployeeException("Failed to update employee with ID: " + id + ". Status: " + response.getStatusCode());
        }
        log.info("Employee with ID: {} updated successfully", id);
    }

    @Override
    public void deleteEmployee(String id) {
        log.info("Deleting employee with ID: {}", id);
        ResponseEntity<Void> response = restClient
            .delete()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .toEntity(Void.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            log.error("Failed to delete employee with ID: {}. Status code: {}", id, response.getStatusCode());
            throw new EmployeeException("Failed to delete employee with ID: " + id + ". Status: " + response.getStatusCode());
        }
        log.info("Employee with ID: {} deleted successfully", id);
    }

    @Override
    public EmployeeResponse getEmployee(String id) {
        log.info("Retrieving employee with ID: {}", id);
        ResponseEntity<EmployeeResponse> response = restClient
            .get()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .toEntity(EmployeeResponse.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            log.error("Failed to retrieve employee with ID: {}. Status code: {}", id, response.getStatusCode());
            throw new EmployeeException("Failed to retrieve employee with ID: " + id + ". Status: " + response.getStatusCode());
        }
        log.info("Employee with ID: {} retrieved successfully", id);
        return response.getBody();
    }

    @Override
    public List<EmployeeResponse> getEmployees(int page, int size, String sortBy, String direction, String departmentId,
            LocalDate hireDate, String employmentStatus) {
        log.info("Retrieving employees with filters - page: {}, size: {}, sortBy: {}, direction: {}, departmentId: {}, hireDate: {}, employmentStatus: {}", 
                page, size, sortBy, direction, departmentId, hireDate, employmentStatus);

        ResponseEntity<RestResponsePage<EmployeeResponse>> response = restClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path("/employee")  // Fixed: was "/department", should be "/employee"
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .queryParam("sortBy", sortBy)
                    .queryParam("direction", direction)
                    .queryParam("employmentStatus", employmentStatus)
                    .queryParam("hireDate", hireDate)
                    .queryParam("departmentId", departmentId)
                    .build()   
            )
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<RestResponsePage<EmployeeResponse>>() {});
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to retrieve employees. Status code: {}", response.getStatusCode());
            throw new DepartementException("Failed to retrieve employees. Status: " + response.getStatusCode());
        }
        RestResponsePage<EmployeeResponse> body = response.getBody();
        if(body != null){
            log.info("Successfully retrieved {} employees", body.getContent().size());
            return body.getContent();
        }else{
            log.warn("Response body is null, returning empty list");
            return new ArrayList<>();
        }
    }
}
