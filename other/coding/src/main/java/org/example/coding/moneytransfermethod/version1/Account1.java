package org.example.coding.moneytransfermethod.version1;

import java.math.BigDecimal;

public class Account1 {

    private final int id;

    private volatile BigDecimal balance;

    public Account1(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public synchronized void add(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public synchronized void subtract(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException();
        }

        balance = balance.subtract(amount);
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}
