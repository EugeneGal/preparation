package org.example.coding.moneytransfer.ops;

import org.example.coding.moneytransfer.exception.AccountNotFoundException;
import org.example.coding.moneytransfer.model.Account;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAccountOperations implements AccountOperations {

    private static final AtomicInteger ACCOUNT_ID_GENERATOR = new AtomicInteger(1);

    private static final Map<Integer, Account> ACCOUNTS = new ConcurrentHashMap<>();

    @Override
    public Account createAccount(String username, int balance) {
        Account account = new Account(ACCOUNT_ID_GENERATOR.getAndIncrement(), username, balance);
        ACCOUNTS.put(account.getId(), account);

        return account;
    }

    @Override
    public Account getAccount(int id) {
        return Optional.ofNullable(ACCOUNTS.get(id))
                .orElseThrow(() -> new AccountNotFoundException("There is no account with id=" + id));
    }

}
