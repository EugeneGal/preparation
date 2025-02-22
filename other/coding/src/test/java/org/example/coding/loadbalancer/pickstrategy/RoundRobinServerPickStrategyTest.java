package org.example.coding.loadbalancer.pickstrategy;

import org.example.coding.loadbalancer.model.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundRobinServerPickStrategyTest {

    @Test
    void shouldPickServerInRoundRobinOrderInConcurrentEnvironment() {
        ServerPickStrategy strategy = new RoundRobinServerPickStrategy();

        Server server1 = new Server("url1");
        Server server2 = new Server("url2");
        Server server3 = new Server("url3");

        SelectServerCallable callable = new SelectServerCallable(new CyclicBarrier(9999),
                List.of(server1, server2, server3), strategy);

        List<FutureTask<Server>> futureTasks = new ArrayList<>();

        for (int i = 0; i < 9999; i++) {
            FutureTask<Server> futureTask = new FutureTask<>(callable);
            futureTasks.add(futureTask);
            new Thread(futureTask).start();
        }

        int server1Count = 0, server2Count = 0, server3Count = 0;
        for (FutureTask<Server> futureTask : futureTasks) {
            try {
                Server server = futureTask.get();
                if (server.equals(server1)) {
                    server1Count++;
                } else if (server.equals(server2)) {
                    server2Count++;
                } else {
                    server3Count++;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(3333, server1Count);
        assertEquals(3333, server2Count);
        assertEquals(3333, server3Count);
    }

    private static class SelectServerCallable implements Callable<Server> {

        private CyclicBarrier cyclicBarrier;

        private List<Server> servers;

        private ServerPickStrategy strategy;

        public SelectServerCallable(CyclicBarrier cyclicBarrier, List<Server> servers, ServerPickStrategy strategy) {
            this.cyclicBarrier = cyclicBarrier;
            this.servers = servers;
            this.strategy = strategy;
        }

        @Override
        public Server call() {
            try {
                cyclicBarrier.await();

                return strategy.pick(servers).get();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
