package com.revolut.test.db;

import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public interface TransferDAO {

    @CreateSqlObject
    AccountDAO dao();

    @Transaction
    default void transfer(Integer from, Integer to, Integer amount) {
        if (dao().findAmountById(from) >= amount) {
            dao().update(new AccountDTO(from, dao().findAmountById(from) - amount));
            dao().update(new AccountDTO(to, dao().findAmountById(to) + amount));
        }
    }

}
