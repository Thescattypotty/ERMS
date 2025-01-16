package org.employee.rms.Security.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.employee.rms.Enum.EPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails{
    
    private UUID id;
    private String username;
    private String password;
    private Set<EPermission> permissions;

    private Collection<? extends GrantedAuthority> authorities;
}
