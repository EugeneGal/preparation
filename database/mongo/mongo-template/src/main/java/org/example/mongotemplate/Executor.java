package org.example.mongotemplate;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mongotemplate.document.User;
import org.example.mongotemplate.service.UserOperations;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Executor.
 *
 * @author Yauheni Halaidzin
 * @since 26.06.2024
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Executor implements CommandLineRunner {

    private final UserOperations userOperations;

    // docker pull mongo
    // docker run -p 27017:27017 --name my-mongo -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
    // stop and remove running container: docker container rm -f my-mongo
    public static void main(String[] args) {
        SpringApplication.run(Executor.class, args);
    }

    @Override
    public void run(String... args) {
        userOperations.addNewUser(new User("1", "John", Map.of()));
        userOperations.addNewUser(new User("2", "Bill", Map.of("key1", "value1")));

        log.info("All users: {}", userOperations.getAllUsers());

        userOperations.addUserSetting("1", "key1", "value1");

        log.info("User with id=1: {}", userOperations.getUserById("1"));

        userOperations.addUserSetting("2", "key2", "value2");

        log.info("All user settings of user with id=2: {}", userOperations.getAllUserSettings("2"));

        log.info("User setting: {}", userOperations.getUserSetting("1", "key1"));
    }

}
