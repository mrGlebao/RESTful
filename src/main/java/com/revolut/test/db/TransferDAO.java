package com.revolut.test.db;

import com.revolut.test.db.AccountDAO;

public class TransferDAO {

    private final AccountDAO accounts;

    public TransferDAO(AccountDAO accounts) {
        this.accounts = accounts;
    }

    public void transfer(Integer from, Integer to, Integer amount) {
        //ToDo: needs transaction
        accounts.update(from, accounts.findAmountById(from) - amount);
        accounts.update(to, accounts.findAmountById(to) + amount);
    }

}
