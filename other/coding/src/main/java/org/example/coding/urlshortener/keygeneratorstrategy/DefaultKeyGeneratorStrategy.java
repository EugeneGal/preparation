package org.example.coding.urlshortener.keygeneratorstrategy;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultKeyGeneratorStrategy implements KeyGeneratorStrategy {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public String generate() {
        long num = counter.getAndIncrement();
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            int remainder = (int) (num % BASE62.length());
            sb.append(BASE62.charAt(remainder));
            num = num / BASE62.length();
        }
        return sb.reverse().toString();
    }

}
