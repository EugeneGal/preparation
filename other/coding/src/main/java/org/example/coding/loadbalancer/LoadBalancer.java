package org.example.coding.loadbalancer;

import org.example.coding.loadbalancer.model.Server;
import org.example.coding.loadbalancer.pickstrategy.ServerPickStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancer {

    private static final int MAX_CAPACITY = 10;

    private final Map<String, Server> urlToServerMap = new ConcurrentHashMap<>();

    private final ServerPickStrategy pickStrategy;

    public LoadBalancer(ServerPickStrategy pickStrategy) {
        if (pickStrategy == null) {
            throw new IllegalArgumentException("Pick strategy cannot be null");
        }
        this.pickStrategy = pickStrategy;
    }

    public boolean registerServer(Server server) {
        if (server == null) {
            return false;
        }

        return urlToServerMap.compute(server.getUrl(), (key, existing) -> {
            if (existing == null && urlToServerMap.size() <= MAX_CAPACITY) {
                return server;
            }
            return existing;
        }) == server;
    }

    public Optional<Server> getServer() {
        if (urlToServerMap.isEmpty()) {
            return Optional.empty();
        }

        return pickStrategy.pick(List.copyOf(urlToServerMap.values()));
    }

    public boolean removeServer(Server server) {
        if (server == null || server.getUrl() == null) {
            return false;
        }

        return urlToServerMap.remove(server.getUrl(), server);
    }

}
