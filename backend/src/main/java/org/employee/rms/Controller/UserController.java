package org.employee.rms.Controller;

import org.employee.rms.Payload.Request.ChangePasswordRequest;
import org.employee.rms.Payload.Request.UserRequest;
import org.employee.rms.Payload.Response.UserResponse;
import org.employee.rms.Service.UserService;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {
    
    private final UserService userService;

    @Operation(summary = "Create User", description = "Create a new user", tags = { "User" })
    @ApiResponse(responseCode = "201", description = "User created")
    @Schema(name = "UserRequest", implementation = UserRequest.class)
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequest userRequest){
        userService.createUser(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get Users", description = "Get all users", tags = { "User" })
    @ApiResponse(responseCode = "200", description = "Users fetched")
    @Schema(name = "UserResponse", implementation = UserResponse.class)
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Page<UserResponse>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ){
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, direction));
    }

    @Operation(summary = "Get User", description = "Get a user by id", tags = { "User" })
    @ApiResponse(responseCode = "200", description = "User fetched")
    @Schema(name = "UserResponse", implementation = UserResponse.class)
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(summary = "Update User", description = "Update an existing user", tags = { "User" })
    @ApiResponse(responseCode = "200", description = "User updated")
    @Schema(name = "UserRequest", implementation = UserRequest.class)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> changePassword(@PathVariable("id") String id, @RequestBody @Valid ChangePasswordRequest changePasswordRequest){
        userService.ChangePassword(id, changePasswordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete User", description = "Delete an existing user", tags = { "User" })
    @ApiResponse(responseCode = "204", description = "User deleted")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
