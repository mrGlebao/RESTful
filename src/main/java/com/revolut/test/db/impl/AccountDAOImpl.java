package com.revolut.test.db.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    public final Jdbi jdbi;

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
    public void insertNamed(int id, int amount) {
        jdbi.useHandle(h ->
                h.createUpdate("insert into account (id, amount) values (:id, :amount)")
                        .bind("id", id)
                        .bind("amount", amount)
                        .execute());
    }

    @Override
    public void insert(AccountDTO dto) {
        insertNamed(dto.getId(), dto.getAmount());
    }

    @Override
    public List<AccountDTO> getById(int id) {
        return jdbi.withHandle(
                h -> h.createQuery("select * from account where id = :id")
                        .bind("id", id)
                        .map((rs, ctx) -> new AccountDTO(rs.getInt("id"), rs.getInt("amount")))
                        .list()
        );
    }

    @Override
    public int findAmountById(int id) {
        return jdbi.withHandle(
                h -> h.createQuery("select amount from account where id = :id")
                        .bind("id", id)
                        .mapTo(Integer.class)
                        .findOnly()
        );
    }

    @Override
    public int updateNamed(int id, int amount) {
        return jdbi.withHandle(
                h -> h.createUpdate("update account set amount = :amount where id = :id")
                        .bind("id", id)
                        .bind("amount", amount)
                        .execute()
        );
    }

    @Override
    public int update(AccountDTO dto) {
        return updateNamed(dto.getId(), dto.getAmount());
    }
}
