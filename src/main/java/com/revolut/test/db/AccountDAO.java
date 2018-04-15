package com.revolut.test.db;

import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface AccountDAO {

    void createTransferTable();

    void insertNamed(int id, int amount);

    void insertNamedWithHandle(int id, int amount, Handle h);

    void insert(AccountDTO dto);

    AccountDTO getById(int id);

    int findAmountById(int id);

    int updateNamed(int id, int amount);

    int updateNamedWithHandle(int id, int amount, Handle h);

    int update(AccountDTO dto);

}