package com.revolut.test.dao.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.entities.Account;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountDAOImpl implements AccountDAO {

    private final Logger logger = LoggerFactory.getLogger(AccountDAOImpl.class);

    private final Jdbi jdbi;

    private final String AMOUNT_IS_TOO_SMALL = "Amount can't be less than 0!";

    public AccountDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public void createTransferTable() {
        jdbi.useHandle(h ->
                h.createUpdate("create table account (id int primary key, amount int)")
                        .execute());
    }

    @Override
    public void insert(Account dto) {
        jdbi.useHandle(h -> insert(dto, h));
    }

    @Override
    public void insert(Account dto, Handle h) {
        if (dto.getAmount() < 0) {
            logger.error("Error on insert:");
            throw new RuntimeException(AMOUNT_IS_TOO_SMALL);
        }
        h.createUpdate("insert into account (id, amount) values (:id, :amount)")
                .bind("id", dto.getId())
                .bind("amount", dto.getAmount())
                .execute();
    }

    @Override
    public Account getById(int id) {
        return jdbi.withHandle(
                h -> h.createQuery("select * from account where id = :id")
                        .bind("id", id)
                        .map((rs, ctx) -> Account.of(rs.getInt("id"), rs.getInt("amount")))
                        .findOnly());
    }

    @Override
    public int update(Account dto) {
        return jdbi.withHandle(
                h -> update(dto, h)
        );
    }

    @Override
    public int update(Account dto, Handle h) {
        if (dto.getAmount() < 0) {
            logger.error("Error on insert:");
            throw new RuntimeException(AMOUNT_IS_TOO_SMALL);
        }
        return h.createUpdate("update account set amount = :amount where id = :id")
                .bind("id", dto.getId())
                .bind("amount", dto.getAmount())
                .execute();
    }

}
