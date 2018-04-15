package com.revolut.test.db;

import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.core.Handle;

public interface AccountDAO {

    void createTransferTable();

    AccountDTO getById(int id);

    void insert(AccountDTO dto);

    void insert(AccountDTO dto, Handle h);

    int update(AccountDTO dto);

    int update(AccountDTO dto, Handle h);
}