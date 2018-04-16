package com.revolut.test.service;

import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.Result;

public interface AccountService {

    Result<AccountDTO> get(int i);

    Result<AccountDTO> add(AccountDTO dto);

    Result<AccountDTO> update(AccountDTO dto);

}
