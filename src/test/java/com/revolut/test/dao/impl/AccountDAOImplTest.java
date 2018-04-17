package com.revolut.test.dao.impl;

import com.revolut.test.dao.H2JDBIRule;
import com.revolut.test.dao.AccountDAO;
import com.revolut.test.entities.Account;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AccountDAOImplTest {

    private AccountDAOImpl accountDAO;
    private Jdbi jdbi;
    Account dto = new Account(1, 100);

    @Rule
    public H2JDBIRule rule = new H2JDBIRule();

    @Before
    public void init() {
        jdbi = rule.getJdbi();
        accountDAO = new AccountDAOImpl(jdbi);
        accountDAO.insert(dto);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAccountDAO_insert() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        Account newDTO = new Account(dto.getId() + 1, dto.getAmount());
        accountDAOSpy.insert(newDTO);
        verify(accountDAOSpy, times(1)).insert(any(Account.class));
        verify(accountDAOSpy, times(1)).insert(any(Account.class), any(Handle.class));
        assertEquals("Unexpected result of insert", newDTO, accountDAO.getById(newDTO.getId()));
    }

    @Test
    public void testAccountDAO_getById() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        Account actual = accountDAOSpy.getById(dto.getId());
        verify(accountDAOSpy, times(1)).getById(anyInt());
        assertEquals("Unexpected result of getByID", dto, actual);
    }

    @Test
    public void testAccountDAO_update() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        Account newDTO = new Account(dto.getId(), dto.getAmount() + 100);
        accountDAOSpy.update(newDTO);
        verify(accountDAOSpy, times(1)).update(any(Account.class));
        verify(accountDAOSpy, times(1)).update(any(Account.class), any(Handle.class));
        assertEquals("Unexpected result of update", newDTO, accountDAO.getById(newDTO.getId()));
    }


}
