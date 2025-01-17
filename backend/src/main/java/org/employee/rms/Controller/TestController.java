package org.employee.rms.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/test")
@Tag(name = "Test", description = "Test Controller")
public class TestController {
    
    @Operation(summary = "Protected Route", description = "Protected Route")
    @ApiResponse(responseCode = "200", description = "Protected Route")
    @GetMapping("/protected")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> protectedRoute() {
        return ResponseEntity.ok("Test");
    }

    @Operation(summary = "Protected Route Read", description = "Protected Route Read")
    @ApiResponse(responseCode = "200", description = "Protected Route Read")
    @GetMapping("/protected/read")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<String> protectedRouteRead() {
        return ResponseEntity.ok("Test");
    }

    @Operation(summary = "Protected Route Create", description = "Protected Route Create")
    @ApiResponse(responseCode = "200", description = "Protected Route Create")
    @GetMapping("/protected/create")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<String> protectedRouteCreate() {
        return ResponseEntity.ok("Test");
    }

    @Operation(summary = "Protected Route Update", description = "Protected Route Update")
    @ApiResponse(responseCode = "200", description = "Protected Route Update")
    @GetMapping("/protected/update")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<String> protectedRouteUpdate() {
        return ResponseEntity.ok("Test");
    }

    @Operation(summary = "Protected Route Delete", description = "Protected Route Delete")
    @ApiResponse(responseCode = "200", description = "Protected Route Delete")
    @GetMapping("/protected/delete")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<String> protectedRouteDelete() {
        return ResponseEntity.ok("Test");
    }

    @Operation(summary = "Unprotected Route", description = "Unprotected Route")
    @ApiResponse(responseCode = "200", description = "Unprotected Route")
    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedRoute() {
        return ResponseEntity.ok("Test");
    }
}
