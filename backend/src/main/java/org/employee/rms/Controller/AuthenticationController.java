package org.employee.rms.Controller;

import org.employee.rms.Payload.Request.LoginRequest;
import org.employee.rms.Payload.Response.JwtResponse;
import org.employee.rms.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Login to the application", tags = { "Authentication" })
    @Schema(name = "LoginRequest", implementation = LoginRequest.class)
    @ApiResponse(responseCode = "200", description = "Login successful")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Login request received for email: {}", loginRequest.email());
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
