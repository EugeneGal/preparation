package org.example.hibernate.mapping.manytomany;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.example.hibernate.mapping.manytomany.entity.Taxi;
import org.example.hibernate.mapping.manytomany.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate mapping executor.
 *
 * Parent tutorial: https://www.geeksforgeeks.org/hibernate-tutorial/
 *
 * https://www.baeldung.com/hibernate-many-to-many
 *
 * @author Yauheni Halaidzin
 * @since 04.05.2024
 */
@Slf4j
public class HibernateManyToManyMappingExecutor {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure()
        .addAnnotatedClass(Taxi.class)
        .addAnnotatedClass(User.class)
        .buildSessionFactory();

    // docker pull mysql
    // docker run -p 3306:3306 --name custom-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=demo_db -d mysql
    public static void main(String[] args) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Taxi taxi = createTaxi();
                session.persist(taxi);
                transaction.commit();
                log.info("Entity was successfully saved: entity={}", taxi);

                transaction = session.beginTransaction();
                Taxi retrievedTaxi = session.get(Taxi.class, taxi.getId());
                transaction.commit();
                log.info("Entity was successfully retrieved: entity={}", retrievedTaxi);

                transaction = session.beginTransaction();
                session.remove(taxi);
                transaction.commit();
                log.info("Entity was successfully removed: entity={}", taxi);
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

    private static Taxi createTaxi() {
        Taxi taxi = Taxi.builder()
            .name("Uber")
            .build();

        User user = User.builder()
            .name("Petr Petrov")
            .taxi(taxi)
            .build();

        taxi.setUsers(List.of(user));

        return taxi;
    }

}
