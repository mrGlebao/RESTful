package com.revolut.test.dao;

import com.revolut.test.entities.Account;
import org.jdbi.v3.core.Handle;

public interface AccountDAO {

    void createTransferTable();

    Account getById(int id);

    void insert(Account dto);

    void insert(Account dto, Handle h);

    int update(Account dto);

    int update(Account dto, Handle h);
}