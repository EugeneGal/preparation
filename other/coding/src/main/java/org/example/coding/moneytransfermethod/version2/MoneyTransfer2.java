package org.example.coding.moneytransfermethod.version2;

import java.math.BigDecimal;

public class MoneyTransfer2 {

    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0");

    // With do-while loop it's similar to optimistic locking on the side of database
    public void transfer(Account2 from, Account2 to, BigDecimal amount) {
        if (amount == null || amount.compareTo(ZERO_AMOUNT) <= 0) {
            throw new IllegalArgumentException();
        }

        // non-atomic operation? - looks good from the point of view guys
        from.subtract(amount);
        to.add(amount);
    }

}
