package org.example.coding.moneytransfer;

import org.example.coding.moneytransfer.model.Account;
import org.example.coding.moneytransfer.ops.AccountOperations;
import org.example.coding.moneytransfer.ops.InMemoryAccountOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryAccountOperationsTest {

    @Test
    void shouldCreateAccount() {
        AccountOperations accountOperations = new InMemoryAccountOperations();

        assertNotNull(accountOperations.createAccount("Mike", 0));
    }

    @Test
    void shouldGetAccountById() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        Account account = accountOperations.createAccount("Mike", 0);

        assertEquals(account, accountOperations.getAccount(account.getId()));
    }

}
