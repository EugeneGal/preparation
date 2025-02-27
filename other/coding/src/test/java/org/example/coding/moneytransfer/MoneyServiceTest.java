package org.example.coding.moneytransfer;

import org.example.coding.moneytransfer.ops.AccountOperations;
import org.example.coding.moneytransfer.ops.InMemoryAccountOperations;
import org.example.coding.moneytransfer.model.Account;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyServiceTest {

    @Test
    void shouldAddMoneyToBalance() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        Account account = accountOperations.createAccount("Mike", 0);
        MoneyService moneyService = new MoneyService(accountOperations);

        moneyService.addMoney(account.getId(), 100);

        assertEquals(100, account.getBalance());
    }

    @Test
    void shouldExtractMoneyFromBalance() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        Account account = accountOperations.createAccount("Mike", 100);
        MoneyService moneyService = new MoneyService(accountOperations);

        moneyService.extractMoney(account.getId(), 50);

        assertEquals(50, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenAmountForExtractingLessZero() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        Account account = accountOperations.createAccount("Mike", 100);
        MoneyService moneyService = new MoneyService(accountOperations);

        assertThrows(IllegalArgumentException.class, () -> moneyService.extractMoney(account.getId(), -100));
    }

    @Test
    void shouldThrowExceptionWhenExtractingMoneyFromInsufficientBalance() {
        AccountOperations AccountOperations = new InMemoryAccountOperations();
        Account account = AccountOperations.createAccount("Mike", 100);
        MoneyService moneyService = new MoneyService(AccountOperations);

        assertThrows(IllegalArgumentException.class, () -> moneyService.extractMoney(account.getId(), 150));
    }

    @Test
    void shouldTransferMoneyFromOneAccountToAnother() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        MoneyService moneyService = new MoneyService(accountOperations);
        Account account1 = accountOperations.createAccount("Mike", 100);
        Account account2 = accountOperations.createAccount("Jake", 200);

        moneyService.transferMoney(account1.getId(), account2.getId(), 50);

        assertEquals(50, account1.getBalance());
        assertEquals(250, account2.getBalance());
    }

    @Test
    void shouldNotTransferMoneyFromOneAccountToAnotherAndThrowExceptionWhenBalanceInsufficient() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        MoneyService moneyService = new MoneyService(accountOperations);
        Account account1 = accountOperations.createAccount("Mike", 100);
        Account account2 = accountOperations.createAccount("Jake", 200);

        assertThrows(IllegalArgumentException.class, () -> moneyService.transferMoney(account1.getId(), account2.getId(), 150));
        assertEquals(100, account1.getBalance());
        assertEquals(200, account2.getBalance());
    }

    @Test
    void shouldTransferMoneyFromOneAccountToAnotherInConcurrentEnvironment() {
        AccountOperations accountOperations = new InMemoryAccountOperations();
        MoneyService moneyService = new MoneyService(accountOperations);
        Account account1 = accountOperations.createAccount("Mike", 500);
        Account account2 = accountOperations.createAccount("Jake", 200);

        CountDownLatch startLatch = new CountDownLatch(1);

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        Runnable task = () -> {
            try {
                startLatch.await();
                moneyService.transferMoney(account1.getId(), account2.getId(), 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            futures.add(executorService.submit(task));
        }

        startLatch.countDown();

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        assertEquals(0, account1.getBalance());
        assertEquals(700, account2.getBalance());
    }

}
