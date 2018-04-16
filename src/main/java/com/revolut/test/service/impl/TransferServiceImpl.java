package com.revolut.test.service.impl;

import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.Result;
import com.revolut.test.dto.TransferDTO;
import com.revolut.test.service.TransferService;

public class TransferServiceImpl implements TransferService {

    private final TransferDAO dao;

    public TransferServiceImpl(TransferDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<TransferDTO> transfer(TransferDTO dto) {
        try {
            dao.transfer(dto);
            return Result.success(dto);
        } catch (RuntimeException ex) {
            return Result.error(ex);
        }
    }
}
