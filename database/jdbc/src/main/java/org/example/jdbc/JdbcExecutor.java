package org.example.jdbc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC executor.
 *
 * @author Yauheni Halaidzin
 * @since 28.03.2024
 */
@Slf4j
public class JdbcExecutor {

    // docker pull postgres
    // docker run --name my-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db -d postgres
    @SneakyThrows
    public static void main(String[] args) {
        log.info("Hello!");

        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection("jdbc:postgresql://0.0.0.0:5432/db", "user", "password");

        Statement statement = connection.createStatement();

        statement.executeUpdate("drop table if exists student");

        statement.executeUpdate("""
            create table student 
            (
                id integer generated always as identity,
                name varchar(256) not null,
                group_number integer not null,
                PRIMARY KEY (id)
            )
            """);

        statement.executeUpdate("insert into student (name, group_number) values ('Peter', '3')");

        statement.executeUpdate("insert into student (name, group_number) values ('Mike', '5')");

        ResultSet result = statement.executeQuery("select * from student");

        List<Student> students = new ArrayList<>();
        while (result.next()) {
            students.add(new Student(result.getInt("id"), result.getString("name"), result.getInt("group_number")));
        }

        log.info("Students: {}", students);

        connection.close();
        statement.close();
    }
}