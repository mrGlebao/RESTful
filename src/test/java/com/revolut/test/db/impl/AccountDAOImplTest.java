package com.revolut.test.db.impl;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
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
    AccountDTO dto = AccountDTO.of(1, 100);

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
        AccountDTO newDTO = AccountDTO.of(dto.getId() + 1, dto.getAmount());
        accountDAOSpy.insert(newDTO);
        verify(accountDAOSpy, times(1)).insert(any(AccountDTO.class));
        verify(accountDAOSpy, times(1)).insert(any(AccountDTO.class), any(Handle.class));
        assertEquals("Unexpected result of insert", newDTO, accountDAO.getById(newDTO.getId()));
    }

    @Test
    public void testAccountDAO_getById() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        AccountDTO actual = accountDAOSpy.getById(dto.getId());
        verify(accountDAOSpy, times(1)).getById(anyInt());
        assertEquals("Unexpected result of getByID", dto, actual);
    }

    @Test
    public void testAccountDAO_update() {
        AccountDAO accountDAOSpy = spy(accountDAO);
        AccountDTO newDTO = AccountDTO.of(dto.getId(), dto.getAmount() + 100);
        accountDAOSpy.update(newDTO);
        verify(accountDAOSpy, times(1)).update(any(AccountDTO.class));
        verify(accountDAOSpy, times(1)).update(any(AccountDTO.class), any(Handle.class));
        assertEquals("Unexpected result of update", newDTO, accountDAO.getById(newDTO.getId()));
    }


}
