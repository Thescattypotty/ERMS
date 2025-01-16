package org.employee.rms.EntityRepository;

import java.util.Optional;
import java.util.UUID;

import org.employee.rms.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Optional<Role> findByName(String name);
}
