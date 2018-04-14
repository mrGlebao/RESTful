package com.revolut.test.db;

import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public interface TransferDAO {

    @CreateSqlObject
    AccountDAO dao();

    @Transaction
    default void transfer(Integer from, Integer to, Integer amount) {
        if (dao().findAmountById(from) >= amount) {
            dao().updateNamed(from, dao().findAmountById(from) - amount);
            dao().updateNamed(to, dao().findAmountById(to) + amount);
        }
    }

}
