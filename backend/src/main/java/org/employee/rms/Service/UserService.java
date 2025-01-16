package org.employee.rms.Service;

import java.util.UUID;


import org.employee.rms.Entity.Role;
import org.employee.rms.Entity.User;
import org.employee.rms.EntityRepository.RoleRepository;
import org.employee.rms.EntityRepository.UserRepository;
import org.employee.rms.Exception.PasswordIncorrectException;
import org.employee.rms.Exception.RoleNotFoundException;
import org.employee.rms.Exception.UserNotFoundException;
import org.employee.rms.IService.IUserService;
import org.employee.rms.Payload.Mapper.UserMapper;
import org.employee.rms.Payload.Request.ChangePasswordRequest;
import org.employee.rms.Payload.Request.UserRequest;
import org.employee.rms.Payload.Response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        Role role = roleRepository.findById(UUID.fromString(userRequest.roleId()))
            .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        if(userRepository.existsById(UUID.fromString(id))){
            userRepository.deleteById(UUID.fromString(id));
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public void ChangePassword(String id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(passwordEncoder.matches(changePasswordRequest.oldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
            userRepository.save(user);
        }else{
            throw new PasswordIncorrectException("Password incorrect, please try again");
        }
    }

    @Override
    public UserResponse getUser(String id) {
        return userRepository.findById(UUID.fromString(id))
            .map(userMapper::fromUser)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return userRepository.findAll(pageable).map(userMapper::fromUser);
    }

}
