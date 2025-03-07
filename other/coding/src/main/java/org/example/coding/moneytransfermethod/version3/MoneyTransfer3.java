package org.example.coding.moneytransfermethod.version3;

import java.math.BigDecimal;

public class MoneyTransfer3 {

    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0");

    // With two synchronized it's similar to pessimistic locking on the side of database
    public void transfer(Account3 from, Account3 to, BigDecimal amount) {
        if (amount == null || amount.compareTo(ZERO_AMOUNT) <= 0) {
            throw new IllegalArgumentException();
        }

        Account3 first = from.getId() < to.getId() ? from : to;
        Account3 second = from.getId() > to.getId() ? from : to;

        synchronized (first) {
            synchronized (second) {
                from.subtract(amount);
                to.add(amount);
            }
        }

    }

}
