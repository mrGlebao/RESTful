package com.revolut.test.service.impl;

import com.revolut.test.dao.TransferDAO;
import com.revolut.test.entities.Result;
import com.revolut.test.entities.Transfer;
import com.revolut.test.service.TransferService;

public class TransferServiceImpl implements TransferService {

    private final TransferDAO dao;

    public TransferServiceImpl(TransferDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<Transfer> transfer(Transfer dto) {
        try {
            dao.transfer(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }
}
