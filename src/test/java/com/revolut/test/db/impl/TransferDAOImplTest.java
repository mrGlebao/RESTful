package com.revolut.test.db.impl;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
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
        accountDAO.insert(new AccountDTO(1, 100));
        accountDAO.insert(new AccountDTO(2, 200));
        //transferDAO = new TransferDAOImpl(jdbi, accountDAO);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTransfer_FailsOnFirstStep() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        doThrow(new RuntimeException()).when(accountDAOSpy).updateNamed(1, 50);
//        when(accountDAOSpy.updateNamed(2, 150)).thenCallRealMethod();
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        try {
            trDao.transfer(1, 2, 50);
        } catch (RuntimeException ex) {
            verify(accountDAOSpy, times(1)).updateNamed(anyInt(), anyInt());
            assertEquals("donor-client money changed", 100, accountDAO.findAmountById(1));
            assertEquals("acceptor-client money changed", 200, accountDAO.findAmountById(2));
        }
    }

    @Test
    public void testTransfer_FailsOnSecondStep() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        doThrow(new RuntimeException()).when(accountDAOSpy).updateNamed(2, 250);
//        when(accountDAOSpy.updateNamed(2, 150)).thenCallRealMethod();
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        try {
            trDao.transfer(1, 2, 50);
        } catch (RuntimeException ex) {
            verify(accountDAOSpy, times(2)).updateNamed(anyInt(), anyInt());
            assertEquals("donor-client money changed", 100, accountDAO.findAmountById(1));
            assertEquals("acceptor-client money changed", 200, accountDAO.findAmountById(2));
        }
    }


    @Test
    public void testTransfer_Success() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);
        trDao.transfer(1, 2, 50);
        verify(accountDAOSpy, times(2)).updateNamed(anyInt(), anyInt());
        assertEquals("donor-client money changed", 50, accountDAO.findAmountById(1));
        assertEquals("acceptor-client money changed", 250, accountDAO.findAmountById(2));
    }

}
