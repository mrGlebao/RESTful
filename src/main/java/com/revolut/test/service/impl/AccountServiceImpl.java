package com.revolut.test.service.impl;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.Result;
import com.revolut.test.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO dao;

    public AccountServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<AccountDTO> get(int i) {
        try {
            AccountDTO dto = dao.getById(i);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }

    @Override
    public Result<AccountDTO> add(AccountDTO dto) {
        try {
            dao.insert(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }

    @Override
    public Result<AccountDTO> update(AccountDTO dto) {
        try {
            dao.update(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }
}
