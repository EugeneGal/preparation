package org.example.springjdbctemplate;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration.
 *
 * @author Yauheni Halaidzin
 * @since 14.05.2024
 */
@Configuration
public class Config {

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/demo_db");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        return dataSource;
    }

}
