package org.example.hibernate.core;

import lombok.extern.slf4j.Slf4j;
import org.example.hibernate.core.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate core executor.
 *
 * Parent tutorial: https://www.geeksforgeeks.org/hibernate-tutorial/
 *
 * https://www.geeksforgeeks.org/hibernate-annotations/
 *
 * @author Yauheni Halaidzin
 * @since 25.04.2024
 */
@Slf4j
public class HibernateCoreExecutor {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure()
        .addAnnotatedClass(Student.class)
        .buildSessionFactory();

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    public static void main(String[] args) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Student student = createStudent();
                session.persist(student);
                transaction.commit();

                log.info("Transaction committed successfully: entity={}", student);
            } catch (Exception exception) {
                if (transaction != null) {
                    transaction.rollback();

                    log.error("Transaction rolled back due to an exception.", exception);
                }
            }
        } catch (Exception exception) {
            log.error("Failed to close Hibernate session", exception);
        }
        finally {
            SESSION_FACTORY.close();
        }
    }

    private static Student createStudent() {
        return Student.builder()
            .name("Vasya")
            .surname("Ivanov")
            .groupNumber(9)
            .build();
    }

}
