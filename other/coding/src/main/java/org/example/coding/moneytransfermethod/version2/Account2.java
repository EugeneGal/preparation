package org.example.coding.moneytransfermethod.version2;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class Account2 {

    private final int id;

    private final AtomicReference<BigDecimal> balance;

    public Account2(int id, BigDecimal balance) {
        this.id = id;
        this.balance = new AtomicReference<>(balance);
    }

    public void add(BigDecimal amount) {
        balance.getAndUpdate(b -> b.add(amount));
    }

    public void subtract(BigDecimal amount) {
        BigDecimal expectedBalance;
        BigDecimal newBalance;

        do {
            expectedBalance = balance.get();

            if (expectedBalance.compareTo(amount) < 0) {
                throw new IllegalArgumentException();
            }

            newBalance = expectedBalance.subtract(amount);
        } while (balance.compareAndSet(expectedBalance, newBalance));
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance.get();
    }

}
