package com.revolut.test.service.impl;

import com.revolut.test.dao.TransferDAO;
import com.revolut.test.dto.Result;
import com.revolut.test.entities.Transfer;
import com.revolut.test.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferServiceImpl implements TransferService {

    private final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    private final TransferDAO dao;

    public TransferServiceImpl(TransferDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<Transfer> transfer(Transfer dto) {
        try {
            dao.transfer(dto);
            return Result.success(dto);
        } catch (Exception ex) {
            logger.error("Exception on transfer: ", ex);
            return Result.error(ex);
        }
    }
}
