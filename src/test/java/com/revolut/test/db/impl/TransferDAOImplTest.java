package com.revolut.test.db.impl;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.TransferDTO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

public class TransferDAOImplTest {

    private TransferDAOImpl transferDAO;
    private AccountDAOImpl accountDAO;
    private Jdbi jdbi;

    @Rule
    public H2JDBIRule rule = new H2JDBIRule();

    @Before
    public void init() {
        jdbi = rule.getJdbi();
        accountDAO = new AccountDAOImpl(jdbi);
        accountDAO.insert(AccountDTO.of(1, 100));
        accountDAO.insert(AccountDTO.of(2, 200));
        //transferDAO = new TransferDAOImpl(jdbi, accountDAO);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTransfer_FailsOnFirstStep() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        doThrow(new RuntimeException()).when(accountDAOSpy).update(AccountDTO.of(1, 50));
//        when(accountDAOSpy.updateNamed(2, 150)).thenCallRealMethod();
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        try {
            trDao.transfer(TransferDTO.builder().withIdFrom(1).withIdTo(2).withAmount(50).build());
        } catch (RuntimeException ex) {
            verify(accountDAOSpy, times(1)).update(any(AccountDTO.class));
            assertEquals("donor-client money changed", 100, accountDAO.getById(1).getAmount());
            assertEquals("acceptor-client money changed", 200, accountDAO.getById(2).getAmount());
        }
    }

    @Test
    public void testTransfer_FailsOnSecondStep() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        doThrow(new RuntimeException()).when(accountDAOSpy).update(AccountDTO.of(2, 250));
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        try {
            trDao.transfer(TransferDTO.builder().withIdFrom(1).withIdTo(2).withAmount(50).build());
        } catch (RuntimeException ex) {
            verify(accountDAOSpy, times(2)).update(any(AccountDTO.class), any(Handle.class));
            assertEquals("donor-client money changed", 100, accountDAO.getById(1).getAmount());
            assertEquals("acceptor-client money changed", 200, accountDAO.getById(2).getAmount());
        }
    }


    @Test
    public void testTransfer_Success() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        trDao.transfer(TransferDTO.builder().withIdFrom(1).withIdTo(2).withAmount(50).build());
        verify(accountDAOSpy, times(2)).update(any(AccountDTO.class), any(Handle.class));
        assertEquals("donor-client money changed", 50, accountDAO.getById(1).getAmount());
        assertEquals("acceptor-client money changed", 250, accountDAO.getById(2).getAmount());
    }

}
