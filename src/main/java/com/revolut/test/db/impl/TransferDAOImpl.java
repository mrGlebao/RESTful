package com.revolut.test.db.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import org.jdbi.v3.core.Jdbi;

public class TransferDAOImpl implements TransferDAO {

    private final AccountDAO dao;
    private final Jdbi jdbi;

    public TransferDAOImpl(Jdbi jdbi) {
        this.dao = new AccountDAOImpl(jdbi);
        this.jdbi = jdbi;
    }


    @Override
    public AccountDAO dao() {
        return dao;
    }

    @Override
    public void transfer(Integer from, Integer to, Integer amount) {
        jdbi.useTransaction(
                h -> {
                    if (dao().findAmountById(from) >= amount) {
                        dao().updateNamed(from, dao().findAmountById(from) - amount);
                        dao().updateNamed(to, dao().findAmountById(to) + amount);
                    }
                }
        );
    }

}
