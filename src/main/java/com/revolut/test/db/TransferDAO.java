package com.revolut.test.db;

public interface TransferDAO {

    void transfer(Integer from, Integer to, Integer amount);

}
