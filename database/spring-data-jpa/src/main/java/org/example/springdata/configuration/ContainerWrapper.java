package org.example.springdata.configuration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

/**
 * Container wrapper.
 *
 * @author Yauheni Halaidzin
 * @since 15.07.2024
 */
public class ContainerWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private final GenericContainer<?> container;

    public ContainerWrapper() {
        container = new GenericContainer<>(DockerImageName.parse("mysql:8.4.1"))
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .withEnv("MYSQL_DATABASE", "demo_db")
            .withExposedPorts(3306)
            .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(3306), new ExposedPort(3306))))
            .withLogConsumer(new Slf4jLogConsumer(LOGGER))
            .withReuse(false);

        container.start();
    }

    @PreDestroy
    public void destroy() {
        if (container != null) {
            container.stop();
        }
    }

}
