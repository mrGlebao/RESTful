package com.revolut.test.db;

import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;

public interface AccountDAO {

    @SqlUpdate("create table account (id int primary key, amount int)")
    void createTransferTable();

    @SqlUpdate("insert into account (id, amount) values (:id, :amount)")
    void insertNamed(@Bind("id") int id, @Bind("amount") int amount);

    @SqlUpdate("insert into account (id, amount) values (:id, :amount)")
    void insert(@BindBean AccountDTO dto);

    @SqlQuery("select * from account where id = :id")
    @RegisterBeanMapper(AccountDTO.class)
    List<AccountDTO> getById(@Bind("id") int id);

    @SqlQuery("select amount from account where id = :id")
    int findAmountById(@Bind("id") int id);

    @SqlUpdate("update account set amount = :amount where id = :id")
    int update(@Bind("id") int id, @Bind("amount") int amount);

}