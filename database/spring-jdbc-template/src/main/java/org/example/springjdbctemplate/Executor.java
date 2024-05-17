package org.example.springjdbctemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springjdbctemplate.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Executor for Spring JDBC template.
 *
 * https://spring.io/guides/gs/relational-data-access
 * https://www.baeldung.com/spring-jdbc-jdbctemplate
 *
 * @author Yauheni Halaidzin
 * @since 14.05.2024
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Executor implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    public static void main(String[] args) {
        SpringApplication.run(Executor.class, args);
    }

    @Override
    public void run(String... args) {
        // usage of JdbcTemplate

        jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
            .map(name -> name.split(" "))
            .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?", (resultSet, rowNumber) ->
                Customer.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .build(), "Josh")
            .forEach(customer -> log.info("Select via jdbcTemplate: {}", customer));

        // usage of NamedParameterJdbcTemplate with MapSqlParameterSource

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
            .addValue("first_name", "John")
            .addValue("last_name", "Woo");

        Integer id = namedParameterJdbcTemplate.queryForObject("SELECT id FROM customers WHERE first_name = :first_name AND last_name = :last_name",
            sqlParameterSource, Integer.class);
        log.info("Select via namedParameterJdbcTemplate with MapSqlParameterSource: id={}", id);

        // usage of NamedParameterJdbcTemplate with BeanPropertySqlParameterSource

        SqlParameterSource sqlParameterSource2 = new BeanPropertySqlParameterSource(Customer.builder()
            .firstName("Josh")
            .build());

        Integer count = namedParameterJdbcTemplate.queryForObject("SELECT COUNT(*) FROM customers WHERE first_name = :firstName",
            sqlParameterSource2, Integer.class);
        log.info("Select via namedParameterJdbcTemplate with BeanPropertySqlParameterSource: count={}", count);
    }

}