package org.employee.rms.EntityRepository;

import java.util.UUID;

import org.employee.rms.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import org.employee.rms.Entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
    Optional<Employee> findByEmail(String email);
    List<Employee> findByDepartmentAndHireDateAndEmploymentStatus(
        Department department,
        LocalDate hireDate,
        String employmentStatus
    );

    @Query("SELECT e FROM Employee e "
        + "WHERE (:department IS NULL OR e.department = :department) "
        + "AND (:hireDate IS NULL OR e.hireDate = :hireDate) "
        + "AND (:employmentStatus IS NULL OR e.employmentStatus = :employmentStatus)")
    Page<Employee> searchEmployees(
        @Param("department") Department department,
        @Param("hireDate") LocalDate hireDate,
        @Param("employmentStatus") String employmentStatus,
        Pageable pageable
    );
}
