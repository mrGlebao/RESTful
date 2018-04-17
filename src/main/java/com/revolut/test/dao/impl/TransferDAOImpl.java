package com.revolut.test.dao.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.dao.TransferDAO;
import com.revolut.test.entities.Account;
import com.revolut.test.entities.Transfer;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.transaction.Transaction;

public class TransferDAOImpl implements TransferDAO {

    private final String AMOUNT_IS_GREATER_THAN_BALANCE = "Amount to transfer can't be greater than account balance!";

    private final AccountDAO dao;
    private final Jdbi jdbi;

    public TransferDAOImpl(Jdbi jdbi, AccountDAO dao) {
        this.dao = dao;
        this.jdbi = jdbi;
    }

    @Override
    @Transaction
    public void transfer(Transfer dto) {
        Account donor = dao.getById(dto.getIdFrom());
        Account acceptor = dao.getById(dto.getIdTo());
        jdbi.useTransaction(
                h -> {
                    if (donor.getAmount() < dto.getAmount()) {
                        throw new RuntimeException(AMOUNT_IS_GREATER_THAN_BALANCE);
                    }
                    dao.update(new Account(donor.getId(), donor.getAmount() - dto.getAmount()), h);
                    dao.update(new Account(acceptor.getId(), acceptor.getAmount() + dto.getAmount()), h);
                }
        );
    }

}
