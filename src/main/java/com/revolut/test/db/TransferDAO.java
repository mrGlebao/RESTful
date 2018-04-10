package com.revolut.test.db;

import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public abstract class TransferDAO {

    @CreateSqlObject
    abstract AccountDAO dao();

    @Transaction
    public void transfer(Integer from, Integer to, Integer amount) {
        dao().update(from, dao().findAmountById(from) - amount);
        dao().update(to, dao().findAmountById(to) + amount);
    }

}
