package org.employee.rms.EntityRepository;

import java.util.UUID;

import org.employee.rms.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
