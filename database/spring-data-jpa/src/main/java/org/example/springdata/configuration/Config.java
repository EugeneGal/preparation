package org.example.springdata.configuration;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * Configuration.
 *
 * @author Yauheni Halaidzin
 * @since 12.07.2024
 */
@Configuration
public class Config {

    @Bean
    public ContainerWrapper containerWrapper() {
        return new ContainerWrapper();
    }

    // Should depend on containerWrapper bean since firstly we start container with mysql and only after that execute liquibase scripts
    @Primary
    @DependsOn("containerWrapper")
    @Configuration
    public static class LiquibaseConfiguration extends LiquibaseAutoConfiguration.LiquibaseConfiguration {

    }

}
