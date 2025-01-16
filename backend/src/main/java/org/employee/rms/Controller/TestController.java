package org.employee.rms.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    
    @GetMapping("/protected")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> protectedRoute() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/protected/read")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<String> protectedRouteRead() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/protected/create")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<String> protectedRouteCreate() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/protected/update")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<String> protectedRouteUpdate() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/protected/delete")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<String> protectedRouteDelete() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedRoute() {
        return ResponseEntity.ok("Test");
    }
}
