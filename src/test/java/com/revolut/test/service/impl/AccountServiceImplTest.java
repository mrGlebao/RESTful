package com.revolut.test.service.impl;

import com.revolut.test.dao.AccountDAO;
import com.revolut.test.entities.Account;
import com.revolut.test.entities.Result;
import com.revolut.test.service.AccountService;
import com.revolut.test.service.impl.AccountServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountDAO dao = mock(AccountDAO.class);

    private Account expected = Account.of(1, 200);

    private AccountService service = new AccountServiceImpl(dao);

    @Test
    public void testAccountServiceImpl_getByIdSuccess() {
    when(dao.getById(expected.getId())).thenReturn(expected);
    Result<Account> actual = service.get(1);
    verify(dao, times(1)).getById(expected.getId());
    assertTrue(actual.isSuccess());
    assertEquals("Unexpected successful get result", expected, actual.getData());
    }

    @Test
    public void testAccountServiceImpl_getByIdFails() {
        when(dao.getById(expected.getId())).thenThrow(new RuntimeException("abc"));
        Result<Account> actual = service.get(1);
        verify(dao, times(1)).getById(expected.getId());
        assertFalse(actual.isSuccess());
        assertEquals("Unexpected error get result", "abc", actual.getEvent().getMessage());
    }

    @Test
    public void testAccountServiceImpl_addSuccess() {
        Result<Account> actual = service.add(expected);
        verify(dao, times(1)).insert(expected);
        assertTrue(actual.isSuccess());
        assertEquals("Unexpected successful add result", expected, actual.getData());
    }

    @Test
    public void testAccountServiceImpl_addFails() {
        doThrow(new RuntimeException("abc")).when(dao).insert(expected);
        Result<Account> actual = service.add(expected);
        verify(dao, times(1)).insert(expected);
        assertFalse(actual.isSuccess());
        assertEquals("Unexpected error add result", "abc", actual.getEvent().getMessage());
    }

    @Test
    public void testAccountServiceImpl_updateSuccess() {
        Result<Account> actual = service.update(expected);
        verify(dao, times(1)).update(expected);
        assertTrue(actual.isSuccess());
        assertEquals("Unexpected successful update result", expected, actual.getData());
    }

    @Test
    public void testAccountServiceImpl_updateFails() {
        doThrow(new RuntimeException("abc")).when(dao).update(expected);
        Result<Account> actual = service.update(expected);
        verify(dao, times(1)).update(expected);
        assertFalse(actual.isSuccess());
        assertEquals("Unexpected error update result", "abc", actual.getEvent().getMessage());
    }



}
