package com.revolut.test.service;

import com.revolut.test.dto.Result;
import com.revolut.test.dto.TransferDTO;

public interface TransferService {

    Result<TransferDTO> transfer(TransferDTO dto);

}
