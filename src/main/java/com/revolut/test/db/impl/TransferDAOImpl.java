package com.revolut.test.db.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public class TransferDAOImpl implements TransferDAO {

    private final AccountDAO dao;
    private final Jdbi jdbi;

    public TransferDAOImpl(Jdbi jdbi, AccountDAO dao) {
        this.dao = dao;
        this.jdbi = jdbi;
    }


    @Override
    public AccountDAO dao() {
        return dao;
    }

    @Override
    @Transaction
    public void transfer(Integer from, Integer to, Integer amount) {
        jdbi.useTransaction(
                h -> {
//                    if (dao().findAmountById(from) >= amount) {
                        dao().updateNamedWithHandle(from, dao().findAmountById(from) - amount, h);
                        dao().updateNamedWithHandle(to, dao().findAmountById(to) + amount, h);
//                    }
                }
        );
    }

}
