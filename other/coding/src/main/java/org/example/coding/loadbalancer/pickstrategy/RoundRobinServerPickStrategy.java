package org.example.coding.loadbalancer.pickstrategy;

import org.example.coding.loadbalancer.model.Server;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinServerPickStrategy implements ServerPickStrategy {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Optional<Server> pick(List<Server> servers) {
        if (servers == null || servers.isEmpty()) {
            return Optional.empty();
        }

        int size = servers.size();
        int index = counter.getAndIncrement() % size;
        return Optional.of(servers.get(index));
    }

}
