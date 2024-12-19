package org.example.kafkaclients.config;

import java.io.Closeable;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

/**
 * Container wrapper.
 *
 * @author Yauheni Halaidzin
 * @since 01.10.2024
 */
@Getter
public class ContainerWrapper implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerWrapper.class);

    private final KafkaContainer container;

    public ContainerWrapper() {
        container = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.7.1"))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER))
            .withCreateContainerCmdModifier(cmd -> cmd.withName("kafka-server"))
            .withExposedPorts(9092, 9093) // Expose the Kafka port
            .withEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:9092");

        container.start();
    }

    @Override
    public void close() {
        if (container != null) {
            container.stop();
        }
    }

}
