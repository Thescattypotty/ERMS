package org.employee.rms.IService;


import org.employee.rms.Payload.Request.ChangePasswordRequest;
import org.employee.rms.Payload.Request.UserRequest;
import org.employee.rms.Payload.Response.UserResponse;
import org.springframework.data.domain.Page;

public interface IUserService {
    void createUser(UserRequest userRequest);
    void deleteUser(String id);
    void ChangePassword(String id, ChangePasswordRequest changePasswordRequest);
    UserResponse getUser(String id);
    Page<UserResponse> getAllUsers(int page, int size, String sortBy, String direction);
}
