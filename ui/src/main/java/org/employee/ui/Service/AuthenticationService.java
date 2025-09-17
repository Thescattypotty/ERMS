package org.employee.ui.Service;

import org.employee.ui.Exception.AuthenticationException;
import org.employee.ui.IService.IAuthenticationService;
import org.employee.ui.Payload.Request.LoginRequest;
import org.employee.ui.Payload.Response.ErrorResponse;
import org.employee.ui.Payload.Response.JwtResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService{
    
    public static String JWT_TOKEN;

    private final RestClient restClient;

    @Override
    public void authenticate(LoginRequest loginRequest) throws AuthenticationException {
        ResponseEntity<JwtResponse> response = restClient
            .post()
            .uri("/auth/login")
            .body(loginRequest)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) ->{
                ErrorResponse error = new ErrorResponse(res.getBody().toString());
                throw new AuthenticationException(error.message());
            })
            .toEntity(JwtResponse.class);
        JwtResponse body = response.getBody();
        if(response.getStatusCode().is2xxSuccessful() && body != null){
            JWT_TOKEN = body.accessToken().substring(7);
        }
    }
    @Override
    public void logout(){
        log.info("Attempting to logout user");
        ResponseEntity<String> response = restClient
            .post()
            .uri("/auth/logout")
            .header("authorization", "Bearer " + JWT_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(String.class);
        if(response.getStatusCode().is2xxSuccessful()){
            log.info("Logout successful");
        } else {
            log.warn("Logout request failed with status: {}", response.getStatusCode());
        }
        // Clear JWT token regardless of response status for security
        JWT_TOKEN = null;
    }
    
}
