package org.employee.rms.Payload.Mapper;

import org.employee.rms.Entity.User;
import org.employee.rms.Payload.Request.UserRequest;
import org.employee.rms.Payload.Response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    
    public User toUser(UserRequest userRequest){
        return User.builder()
            .email(userRequest.email())
            .build();
    }

    public UserResponse fromUser(User user){
        return new UserResponse(
            user.getId().toString(),
            user.getEmail(),
            user.getRole().getName()
        );
    }
}
