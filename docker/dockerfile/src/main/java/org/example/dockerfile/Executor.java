package org.example.dockerfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Executor.
 *
 * @author Yauheni Halaidzin
 * @since 18.07.2024
 */
@SpringBootApplication
public class Executor {

    // docker build -t my-app .
    // docker run -p 8085:8085 --name my-app-container -d my-app
    public static void main(String[] args) {
        SpringApplication.run(Executor.class, args);
    }

}
