package com.revolut.test.db;

import com.revolut.test.dto.TransferDTO;

public interface TransferDAO {

    void transfer(TransferDTO dto);

}
