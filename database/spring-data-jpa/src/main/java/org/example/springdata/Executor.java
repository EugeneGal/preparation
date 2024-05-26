package org.example.springdata;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdata.entity.Company;
import org.example.springdata.entity.Employee;
import org.example.springdata.repository.CompanyRepository;
import org.example.springdata.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring data executor.
 *
 * @author Yauheni Halaidzin
 * @since 20.05.2024
 */
@Slf4j
@EnableJpaRepositories(basePackages = "org.example.springdata.repository")
@EntityScan(basePackages = "org.example.springdata.entity")
@SpringBootApplication
@RequiredArgsConstructor
public class Executor implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    public static void main(String[] args) {
        SpringApplication.run(Executor.class, args);
    }

    @Override
    public void run(String... args) {
        Company company = Company.builder()
            .name("Google Inc")
            .country("USA")
            .address("Silicon Valley")
            .build();
        companyRepository.save(company);

        Employee employee = Employee.builder()
            .name("Mark")
            .surname("Lazovoy")
            .company(company)
            .build();
        employeeRepository.save(employee);

        List<Company> companies = companyRepository.findAll();

        log.info("Companies: {}", companies);
    }

}
