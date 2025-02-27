package org.example.coding.moneytransfer.ops;

import org.example.coding.moneytransfer.model.Account;

public interface AccountOperations {

    Account createAccount(String username, int balance);

    Account getAccount(int id);

}
