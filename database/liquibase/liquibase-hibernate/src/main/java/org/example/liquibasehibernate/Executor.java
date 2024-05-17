package org.example.liquibasehibernate;

import java.sql.Connection;
import java.sql.DriverManager;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.liquibasehibernate.entity.Company;
import org.example.liquibasehibernate.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * First, executes Liquibase change sets (creates DB tables and populates it with data),
 * then with the help of Hibernate retrieves this data.
 *
 * @author Yauheni Halaidzin
 * @since 13.05.2024
 */
@Slf4j
public class Executor {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure()
        .addAnnotatedClass(Company.class)
        .addAnnotatedClass(Employee.class)
        .buildSessionFactory();

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    @SneakyThrows
    public static void main(String[] args) {
        // liquibase
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo_db", "root", "root");
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("liquibase/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update("");

        // hibernate
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Company company = session.get(Company.class, 1);
                transaction.commit();
                log.info("Entity was successfully retrieved: entity={}", company);
            } catch (Exception exception) {
                if (transaction != null) {
                    transaction.rollback();
                    log.error("Transaction rolled back due to an exception.", exception);
                }
            }
        } catch (Exception exception) {
            log.error("Failed to close Hibernate session", exception);
        } finally {
            SESSION_FACTORY.close();
        }
    }

}
