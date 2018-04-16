package com.revolut.test.service.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.entities.Account;
import com.revolut.test.dto.Result;
import com.revolut.test.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDAO dao;

    public AccountServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<Account> get(int i) {
        try {
            Account dto = dao.getById(i);
            return Result.success(dto);
        } catch (Exception ex) {
            logger.error("Exception on get: ", ex);
            return Result.error(ex);
        }
    }

    @Override
    public Result<Account> add(Account dto) {
        try {
            dao.insert(dto);
            return Result.success(dto);
        } catch (Exception ex) {
            logger.error("Exception on add: ", ex);
            return Result.error(ex);
        }
    }

    @Override
    public Result<Account> update(Account dto) {
        try {
            dao.update(dto);
            return Result.success(dto);
        } catch (Exception ex) {
            logger.error("Exception on update: ", ex);
            return Result.error(ex);
        }
    }
}
