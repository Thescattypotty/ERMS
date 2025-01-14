package org.employee.rms.IService;

import org.employee.rms.Payload.Request.LoginRequest;
import org.employee.rms.Payload.Response.JwtResponse;

public interface IAuthenticationService {
    JwtResponse login(LoginRequest loginRequest);
}
