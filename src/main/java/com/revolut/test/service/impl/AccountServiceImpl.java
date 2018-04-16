package com.revolut.test.service.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.entities.Account;
import com.revolut.test.dto.Result;
import com.revolut.test.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO dao;

    public AccountServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<Account> get(int i) {
        try {
            Account dto = dao.getById(i);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }

    @Override
    public Result<Account> add(Account dto) {
        try {
            dao.insert(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }

    @Override
    public Result<Account> update(Account dto) {
        try {
            dao.update(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }
}
