package org.employee.rms.Exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RoleNotFoundException", description = "Exception for role not found")
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
