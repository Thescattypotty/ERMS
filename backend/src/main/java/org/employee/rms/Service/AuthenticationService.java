package org.employee.rms.Service;

import org.employee.rms.IService.IAuthenticationService;
import org.employee.rms.Payload.Request.LoginRequest;
import org.employee.rms.Payload.Response.JwtResponse;
import org.employee.rms.Security.Service.JwtUtilService;
import org.employee.rms.Security.UserDetails.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
                );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtilService.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

        return new JwtResponse("Bearer " + jwt);
        
    }
    
}
