package com.revolut.test.service;

import com.revolut.test.entities.Result;
import com.revolut.test.entities.Transfer;

public interface TransferService {

    Result<Transfer> transfer(Transfer dto);

}
