package org.example.coding.moneytransfermethod.version1;

import java.math.BigDecimal;

public class MoneyTransfer1 {

    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0");

    // If to synchronized to method, it will be like isolation serializable level on the side of database
    public void transfer(Account1 from, Account1 to, BigDecimal amount) {
        if (amount == null || amount.compareTo(ZERO_AMOUNT) <= 0) {
            throw new IllegalArgumentException();
        }

        // might there be deadlock? - looks good from the point of view guys
        // non-atomic operation? - looks good from the point of view guys
        from.subtract(amount);
        to.add(amount);
    }

}
