package org.example.coding.urlshortener.keygeneratorstrategy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultKeyGeneratorStrategyTest {

    @Test
    void shouldGenerateKey() {
        KeyGeneratorStrategy keyGeneratorStrategy = new DefaultKeyGeneratorStrategy();
        assertNotNull(keyGeneratorStrategy.generate());
    }

    @Test
    void shouldGenerateUniqueKeyEveryTime() {
        KeyGeneratorStrategy keyGeneratorStrategy = new DefaultKeyGeneratorStrategy();

        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            keys.add(keyGeneratorStrategy.generate());
        }

        assertEquals(100, keys.size());
    }

    @Test
    void shouldGenerateUniqueKeyEveryTimeInConcurrentEnvironment() {
        KeyGeneratorStrategy keyGeneratorStrategy = new DefaultKeyGeneratorStrategy();

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        CountDownLatch startLatch = new CountDownLatch(1);

        Callable<String> callable = () -> {
            startLatch.await();
            return keyGeneratorStrategy.generate();
        };

        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            futures.add(executorService.submit(callable));
        }

        startLatch.countDown();

        Set<String> keys = new HashSet<>();

        for (Future<String> future : futures) {
            try {
                keys.add(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        assertEquals(100, keys.size());
    }

}
