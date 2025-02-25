package org.example.coding.loadbalancer.pickstrategy;

import org.example.coding.loadbalancer.model.Server;

import java.util.List;
import java.util.Optional;

public interface ServerPickStrategy {

    Optional<Server> pick(List<Server> servers);

}
