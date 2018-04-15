package com.revolut.test.db.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class AccountDAOImpl implements AccountDAO {

    private final Jdbi jdbi;

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
    public void insert(AccountDTO dto) {
        jdbi.useHandle(h -> insert(dto, h));
    }

    @Override
    public void insert(AccountDTO dto, Handle h) {
        if (dto.getAmount() < 0) {
            throw new RuntimeException();
        }
        h.createUpdate("insert into account (id, amount) values (:id, :amount)")
                .bind("id", dto.getId())
                .bind("amount", dto.getAmount())
                .execute();
    }

    @Override
    public AccountDTO getById(int id) {
        return jdbi.withHandle(
                h -> h.createQuery("select * from account where id = :id")
                        .bind("id", id)
                        .map((rs, ctx) -> AccountDTO.of(rs.getInt("id"), rs.getInt("amount")))
                        .findOnly());
    }

    @Override
    public int update(AccountDTO dto) {
        return jdbi.withHandle(
                h -> update(dto, h)
        );
    }

    @Override
    public int update(AccountDTO dto, Handle h) {
        if (dto.getAmount() < 0) {
            throw new RuntimeException();
        }
        return h.createUpdate("update account set amount = :amount where id = :id")
                .bind("id", dto.getId())
                .bind("amount", dto.getAmount())
                .execute();
    }

}
