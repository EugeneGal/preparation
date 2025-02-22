package org.example.coding.loadbalancer.model;

import java.util.Objects;

public class Server {

    private final String url;

    public Server(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Server otherServer)) {
            return false;
        }
        return Objects.equals(this.url, otherServer.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}
