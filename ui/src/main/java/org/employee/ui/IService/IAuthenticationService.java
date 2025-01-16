package org.employee.ui.IService;

import org.employee.ui.Exception.AuthenticationException;
import org.employee.ui.Payload.Request.LoginRequest;

public interface IAuthenticationService {
    void authenticate(LoginRequest loginRequest) throws AuthenticationException;
}
