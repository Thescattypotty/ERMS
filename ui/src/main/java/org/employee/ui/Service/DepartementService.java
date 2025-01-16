package org.employee.ui.Service;

import java.util.ArrayList;
import java.util.List;

import org.employee.ui.Exception.DepartementException;
import org.employee.ui.IService.IDepartementService;
import org.employee.ui.Payload.Request.DepartmentRequest;
import org.employee.ui.Payload.Response.DepartmentResponse;
import org.employee.ui.Payload.Response.ErrorResponse;
import org.employee.ui.RestResponse.RestResponsePage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartementService implements IDepartementService{
    
    private final RestClient restClient;

    @Override
    public void createDepartment(DepartmentRequest departmentRequest) {
        ResponseEntity<Void> response = restClient
            .post()
            .uri("/department")
            .body(departmentRequest)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new DepartementException(error.message());
            })
            .toEntity(Void.class);
            if(!response.getStatusCode().is2xxSuccessful()){
                throw new DepartementException("Bad Response");
            }
            log.info("There is not Error");
    }

    @Override
    public void updateDepartment(String id, DepartmentRequest departmentRequest) {
        ResponseEntity<Void> response = restClient
            .put()
            .uri("/department/"+ id)
            .body(departmentRequest)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new DepartementException(error.message());
            })
            .toEntity(Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new DepartementException("Bad Response");
        }
    }

    @Override
    public void deleteDepartment(String id) {
        ResponseEntity<Void> response = restClient
            .delete()
            .uri("/department/" + id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new DepartementException(error.message());
            })
            .toEntity(Void.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new DepartementException("Bad Response");
        }
    }

    @Override
    public DepartmentResponse getDepartment(String id) {
        ResponseEntity<DepartmentResponse> response = restClient
            .get()
            .uri("/department/" + id)
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new DepartementException(error.message());
            })
            .toEntity(DepartmentResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new DepartementException("Bad Response");
        }
        return response.getBody();
    }

    @Override
    public List<DepartmentResponse> getDepartments(int page, int size, String sortBy, String direction) {
        ResponseEntity<RestResponsePage<DepartmentResponse>> response = restClient
            .get()
            .uri(
                uriBuilder -> uriBuilder
                    .path("/department")
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .queryParam("sortBy", sortBy)
                    .queryParam("direction", direction)
                    .build()   
            )
            .header("authorization", "Bearer " + AuthenticationService.JWT_TOKEN)
            .retrieve()
            /*.onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                log.error("Error : {}", res.getBody().toString());
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new DepartementException(error.message());
            }) */
            .toEntity(new ParameterizedTypeReference<RestResponsePage<DepartmentResponse>>() {});
        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error beacuse its not 2xx status");
            throw new DepartementException("Bad Response");
        }
        RestResponsePage<DepartmentResponse> body = response.getBody();
        if(body != null){
            log.info("Body is not null");
            return body.getContent();
        }else{
            log.error("Body is null");
            return new ArrayList<>();
        }
    }
}
