package org.example.coding.loadbalancer;

import org.example.coding.loadbalancer.model.Server;
import org.example.coding.loadbalancer.pickstrategy.RoundRobinServerPickStrategy;
import org.example.coding.loadbalancer.pickstrategy.ServerPickStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class LoadBalancerTest {

    @Test
    void shouldNotCreateLoadBalancerIfPickStrategyIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new LoadBalancer(null));
    }

    @Test
    void shouldRegisterNewServer() {
        Server server = new Server("url");
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());

        assertTrue(loadBalancer.registerServer(server));
    }

    @Test
    void shouldNotRegisterNewServerWhenItIsNull() {
        ServerPickStrategy pickStrategy = new RoundRobinServerPickStrategy();

        assertFalse(new LoadBalancer(pickStrategy).registerServer(null));
    }

    @Test
    void shouldNotRegisterSameServerTwoTimes() {
        Server server = new Server("url");
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());

        assertTrue(loadBalancer.registerServer(server));
        assertFalse(loadBalancer.registerServer(server));
    }

    @Test
    void shouldRegisterOnlyUpToTenServers() {
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());

        for (int i = 1; i <= 10; i++) {
            Server server = new Server("url" + i);

            assertTrue(loadBalancer.registerServer(server));
        }

        Server server = new Server("url11");
        assertFalse(loadBalancer.registerServer(server));
    }

    @Test
    void shouldRemoveServer() {
        Server server = new Server("url");
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());
        loadBalancer.registerServer(server);

        assertTrue(loadBalancer.removeServer(server));
    }

    @Test
    void shouldGetEmptyServerOptionalWhenNoServersRegistered() {
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());

        assertTrue(loadBalancer.getServer().isEmpty());
    }

    @Test
    void shouldGetServerViaRoundRobinStrategy() {
        Server server1 = new Server("url1");
        Server server2 = new Server("url1");
        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());

        loadBalancer.registerServer(server1);
        loadBalancer.registerServer(server2);
        Optional<Server> serverOpt = loadBalancer.getServer();

        assertTrue(serverOpt.isPresent());
        assertEquals(server1, serverOpt.get());
    }

    @Test
    void shouldGetServerInConcurrentEnvironment() {
        Server server1 = new Server("url1");
        Server server2 = new Server("url2");
        Server server3 = new Server("url3");

        LoadBalancer loadBalancer = new LoadBalancer(new RoundRobinServerPickStrategy());
        loadBalancer.registerServer(server1);
        loadBalancer.registerServer(server2);
        loadBalancer.registerServer(server3);

        CountDownLatch countDownLatch = new CountDownLatch(5000);
        AtomicInteger failedThreadsCount = new AtomicInteger(0);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Runnable runnable = () -> {
                assertTrue(loadBalancer.getServer().isPresent());
                countDownLatch.countDown();
            };

            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler((t, e) -> failedThreadsCount.incrementAndGet());
            threads.add(thread);
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Remove server in the middle of execution to get possible IndexOutOfBoundsException in RoundRobinServerPickStrategy#pick()
        loadBalancer.removeServer(server3);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertEquals(0, failedThreadsCount.get());
    }

}
