package org.example.coding.moneytransfermethod.version3;

import java.math.BigDecimal;

public class Account3 {

    private final int id;

    private volatile BigDecimal balance;

    public Account3(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public void add(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtract(BigDecimal amount) {
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
