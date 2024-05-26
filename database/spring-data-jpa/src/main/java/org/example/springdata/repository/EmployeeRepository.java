package org.example.springdata.repository;

import org.example.springdata.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Employee repository.
 *
 * @author Yauheni Halaidzin
 * @since 21.05.2024
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
