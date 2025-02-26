package org.example.coding.moneytransfer.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private final int id;

    private final String username;

    private final AtomicInteger balance;

    public Account(int id, String username, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException(String.format("Balance cannot be less than 0: balance=%s", balance));
        }
        this.id = id;
        this.username = username;
        this.balance = new AtomicInteger(balance);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getBalance() {
        return balance.get();
    }

    public void addToBalance(int amount) {
        balance.addAndGet(amount);
    }

    public void extractFromBalance(int amount) {
        while (true) {
            int currentBalance = balance.get();
            if (currentBalance < amount) {
                throw new IllegalArgumentException(String.format("Current balance is less than extracted: currentBalance=%s, amountToExtract=%s",
                        currentBalance, amount));
            }
            if (balance.compareAndSet(currentBalance, currentBalance - amount)) {
                return;
            }
        }
    }

}
