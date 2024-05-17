package org.example.hibernate.mapping.onetoone;

import lombok.extern.slf4j.Slf4j;
import org.example.hibernate.mapping.onetoone.entity.Address;
import org.example.hibernate.mapping.onetoone.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate mapping executor.
 *
 * Parent tutorial: https://www.geeksforgeeks.org/hibernate-tutorial/
 *
 * https://www.geeksforgeeks.org/hibernate-one-to-one-mapping/
 *
 * @author Yauheni Halaidzin
 * @since 04.05.2024
 */
@Slf4j
public class HibernateOneToOneMappingExecutor {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure()
        .addAnnotatedClass(Address.class)
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
                log.info("Entity was successfully saved: entity={}", student);

                transaction = session.beginTransaction();
                Student retrievedStudent = session.get(Student.class, student.getId());
                transaction.commit();
                log.info("Entity was successfully retrieved: entity={}", retrievedStudent);

                transaction = session.beginTransaction();
                session.remove(student);
                transaction.commit();
                log.info("Entity was successfully removed: entity={}", student);
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

    private static Student createStudent() {
        Student student = Student.builder()
            .name("Ivan")
            .surname("Petrov")
            .build();

        Address address = Address.builder()
            .street("Masherova")
            .houseNumber(2)
            .student(student)
            .build();

        student.setAddress(address);

        return student;
    }

}
