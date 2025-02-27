package org.example.coding.urlshortener;

import org.example.coding.urlshortener.keygeneratorstrategy.DefaultKeyGeneratorStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalTest {

    @Test
    void shouldShortenUrlsInConcurrentEnvironment() {
        UrlShortenerService shortenerService = new UrlShortenerService(new DefaultKeyGeneratorStrategy());
        String url = "http://verylong.com/verylongverylongverylongverylong?a=1&b=2&c=%s";

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        CountDownLatch startLatch = new CountDownLatch(1);

        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            final int param = i;

            Callable<String> callable = () -> {
                startLatch.await();
                return shortenerService.shortenUrl(String.format(url, param));
            };

            futures.add(executorService.submit(callable));
        }

        startLatch.countDown();

        Set<String> urls = new HashSet<>();

        for (Future<String> future : futures) {
            try {
                urls.add(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        assertEquals(100, urls.size());
    }

}
