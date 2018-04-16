package com.revolut.test.service;

import com.revolut.test.entities.Account;
import com.revolut.test.dto.Result;

public interface AccountService {

    Result<Account> get(int i);

    Result<Account> add(Account dto);

    Result<Account> update(Account dto);

}
