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
        ResponseEntity<Void> response = restClient
            .post()
            .uri("/employee")
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .body(employeeRequest)
            .retrieve()
            .toEntity(Void.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new EmployeeException("Bad Response");
        }
        log.info("There is not Error");
    }

    @Override
    public void updateEmployee(String id, EmployeeRequest employeeRequest) {
        ResponseEntity<Void> response = restClient
            .put()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .body(employeeRequest)
            .retrieve()
            .toEntity(Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new EmployeeException("Bad Response");
        }
        log.info("There is not Error");
    }

    @Override
    public void deleteEmployee(String id) {
        ResponseEntity<Void> response = restClient
            .delete()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .toEntity(Void.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new EmployeeException("Bad Response");
        }
        log.info("There is not Error");
    }

    @Override
    public EmployeeResponse getEmployee(String id) {
        ResponseEntity<EmployeeResponse> response = restClient
            .get()
            .uri("/employee/"+ id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .toEntity(EmployeeResponse.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new EmployeeException("Bad Response");
        }
        return response.getBody();
    }

    @Override
    public List<EmployeeResponse> getEmployees(int page, int size, String sortBy, String direction, String departmentId,
            LocalDate hireDate, String employmentStatus) {

        ResponseEntity<RestResponsePage<EmployeeResponse>> response = restClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path("/department")
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
            log.error("Error beacuse its not 2xx status");
            throw new DepartementException("Bad Response");
        }
        RestResponsePage<EmployeeResponse> body = response.getBody();
        if(body != null){
            log.info("Body is not null");
            return body.getContent();
        }else{
            log.error("Body is null");
            return new ArrayList<>();
        }
    }
}
