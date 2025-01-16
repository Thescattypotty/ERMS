package org.employee.rms.Component;

import java.util.Set;

import org.employee.rms.Entity.Role;
import org.employee.rms.Entity.User;
import org.employee.rms.EntityRepository.RoleRepository;
import org.employee.rms.EntityRepository.UserRepository;
import org.employee.rms.Enum.EPermission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        Role roleRh = roleRepository.save(
            Role.builder()
                .name("ROLE_HR")
                .permissions(Set.of(EPermission.CREATE, EPermission.READ, EPermission.UPDATE, EPermission.DELETE))
                .build()
        );
        Role roleManager = roleRepository.save(
            Role.builder()
                .name("ROLE_MANAGER")
                .permissions(Set.of(EPermission.READ, EPermission.UPDATE))
                .build()
        );
        Role roleAdmin = roleRepository.save(
            Role.builder()
                .name("ROLE_ADMINISTRATOR")
                .permissions(Set.of(EPermission.CREATE, EPermission.READ, EPermission.UPDATE, EPermission.DELETE))
                .build()
        );


        userRepository.save(
            User.builder()
                .email("rh@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(roleRh)
                .build()
        );

        userRepository.save(
            User.builder()
                .email("manager@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(roleManager)
                .build()
        );

        userRepository.save(
            User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("password"))
                .role(roleAdmin)
                .build()
        );
    }
}
