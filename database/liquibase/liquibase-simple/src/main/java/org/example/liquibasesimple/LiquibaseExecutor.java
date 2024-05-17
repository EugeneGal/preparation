package org.example.liquibasesimple;

import java.sql.Connection;
import java.sql.DriverManager;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

/**
 * Liquibase executor.
 *
 * For running liquibase-maven-plugin just uncomment it in pom.xml and execute in Maven window:
 * liquibase -> Plugins -> liquibase -> liquibase:update
 * Or: mvn compile
 *
 * @author Yauheni Halaidzin
 * @since 09.05.2024
 */
public class LiquibaseExecutor {

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    @SneakyThrows
    public static void main(String[] args) {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo_db", "root", "root");

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase("liquibase/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);

        liquibase.update("");

        // rollback change sets
        // liquibase.rollback(2, "");
    }

}
