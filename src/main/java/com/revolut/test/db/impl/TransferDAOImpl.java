package com.revolut.test.db.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.TransferDTO;
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
    @Transaction
    public void transfer(TransferDTO dto) {
        AccountDTO donor = dao.getById(dto.getIdFrom());
        AccountDTO acceptor = dao.getById(dto.getIdTo());
        jdbi.useTransaction(
                h -> {
                    dao.update(AccountDTO.of(donor.getId(), donor.getAmount() - dto.getAmount()), h);
                    dao.update(AccountDTO.of(acceptor.getId(), acceptor.getAmount() + dto.getAmount()), h);
                }
        );
    }

}
