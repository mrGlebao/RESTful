package com.revolut.test.db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AccountDAO {

    @SqlUpdate("create table account (id int primary key, amount int)")
    void createTransferTable();

    @SqlUpdate("insert into account (id, amount) values (:id, :amount)")
    void insert(@Bind("id") int id, @Bind("amount") int amount);

    @SqlQuery("select amount from account where id = :id")
    int findAmountById(@Bind("id") int id);

    @SqlQuery("update account set amount = :amount where id = :id")
    void update(@Bind("id") int id, @Bind("amount") int amount);

}