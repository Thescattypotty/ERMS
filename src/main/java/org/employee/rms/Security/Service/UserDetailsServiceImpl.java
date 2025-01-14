package org.employee.rms.Security.Service;

import java.util.List;

import org.employee.rms.Entity.Role;
import org.employee.rms.Entity.User;
import org.employee.rms.EntityRepository.UserRepository;
import org.employee.rms.Security.UserDetails.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByEmail(username)
            .orElseThrow();
        return UserDetailsImpl.builder()
            .id(user.getId())
            .username(user.getEmail())
            .password(user.getPassword())
            .permissions(user.getRole().getPermissions())
            .authorities(getAuthorities(user.getRole()))
            .build();
    }

    private List<SimpleGrantedAuthority> getAuthorities(Role role){
        return List.of(
            role.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toArray(SimpleGrantedAuthority[]::new)
            );
    }
    
}
