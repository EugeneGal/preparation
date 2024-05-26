package org.example.springdata.repository;

import org.example.springdata.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Company repository.
 *
 * @author Yauheni Halaidzin
 * @since 20.05.2024
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
