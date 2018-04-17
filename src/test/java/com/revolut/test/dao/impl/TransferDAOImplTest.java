package com.revolut.test.dao.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.dao.H2JDBIRule;
import com.revolut.test.dao.TransferDAO;
import com.revolut.test.entities.Account;
import com.revolut.test.entities.Transfer;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferDAOImplTest {

    @Rule
    public H2JDBIRule rule = new H2JDBIRule();

    private AccountDAO mock = mock(AccountDAO.class);
    private AccountDAOImpl accountDAO;
    private Jdbi jdbi;
    private Account donor = new Account(1, 100);
    private Account acceptor = new Account(2, 200);
    private Transfer transfer = Transfer.builder().idFrom(1).idTo(2).amount(50).build();


    @Before
    public void init() {
        jdbi = rule.getJdbi();
        accountDAO = new AccountDAOImpl(jdbi);
        accountDAO.insert(donor);
        accountDAO.insert(acceptor);
        when(mock.getById(donor.getId())).thenReturn(donor);
        when(mock.getById(acceptor.getId())).thenReturn(acceptor);
    }

    @After
    public void tearDown() {
        reset(mock);
    }

    @Test
    public void testTransfer_FailsIfAmountIsTooSmall() {
        TransferDAO trDao = new TransferDAOImpl(jdbi, mock);
        try {
            trDao.transfer(Transfer.builder()
                    .idFrom(donor.getId())
                    .idTo(acceptor.getId())
                    .amount(donor.getAmount() + 100)
                    .build());
        } catch (RuntimeException ignored) {
        }
        verify(mock, times(0)).update(any(Account.class), any(Handle.class));
        assertEquals("donor-client money changed", 100, accountDAO.getById(donor.getId()).getAmount());
        assertEquals("acceptor-client money changed", 200, accountDAO.getById(acceptor.getId()).getAmount());
    }

    @Test
    public void testTransfer_FailsOnFirstStep() {

        AccountDAO acountDAOSpy = spy(accountDAO);

        Account donorUpdated = new Account(donor.getId(), donor.getAmount() - transfer.getAmount());

        doThrow(new RuntimeException("abc")).when(acountDAOSpy).update(eq(donorUpdated), any(Handle.class));
        when(acountDAOSpy.getById(donor.getId())).thenReturn(donor);
        when(acountDAOSpy.getById(acceptor.getId())).thenReturn(acceptor);

        TransferDAO trDao = new TransferDAOImpl(jdbi, acountDAOSpy);
        try {
            trDao.transfer(transfer);
        } catch (RuntimeException ignored) {
        }
        verify(acountDAOSpy, times(1)).update(any(Account.class), any(Handle.class));
        assertEquals("donor-client money changed", 100, accountDAO.getById(donor.getId()).getAmount());
        assertEquals("acceptor-client money changed", 200, accountDAO.getById(acceptor.getId()).getAmount());
    }

    @Test
    public void testTransfer_FailsOnSecondStep() {
        AccountDAO acountDAOSpy = spy(accountDAO);
        Account acceptorUpdated = new Account(acceptor.getId(), acceptor.getAmount() + transfer.getAmount());
        doThrow(new RuntimeException("abc")).when(acountDAOSpy).update(eq(acceptorUpdated), any(Handle.class));

        TransferDAO trDao = new TransferDAOImpl(jdbi, acountDAOSpy);
        try {
            trDao.transfer(transfer);
        } catch (RuntimeException ignored) {
        }
        verify(acountDAOSpy, times(2)).update(any(Account.class), any(Handle.class));
        assertEquals("donor-client money changed", 100, accountDAO.getById(donor.getId()).getAmount());
        assertEquals("acceptor-client money changed", 200, accountDAO.getById(acceptor.getId()).getAmount());
    }


    @Test
    public void testTransfer_Success() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        TransferDAO trDao = new TransferDAOImpl(jdbi, accountDAOSpy);

        trDao.transfer(transfer);
        verify(accountDAOSpy, times(2)).update(any(Account.class), any(Handle.class));
        assertEquals("donor-client money hasn't changed", 50, accountDAO.getById(donor.getId()).getAmount());
        assertEquals("acceptor-client money hasn't changed", 250, accountDAO.getById(acceptor.getId()).getAmount());
    }

}
