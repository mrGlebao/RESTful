package com.revolut.test.service;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.Result;
import com.revolut.test.service.impl.AccountServiceImpl;
import org.jdbi.v3.core.Handle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AccountServiceImplTest {
    private AccountDAO dao = mock(AccountDAO.class);

    private AccountDTO expected = AccountDTO.of(1, 200);

    private AccountService service = new AccountServiceImpl(dao);

    @Test
    public void testAccountServiceImpl_getByIdSuccess() {
    when(dao.getById(expected.getId())).thenReturn(expected);
    Result<AccountDTO> actual = service.get(1);
    verify(dao, times(1)).getById(expected.getId());
    assertTrue(actual.isSuccess());
    assertEquals("Unexpected get result", expected, actual.getData());
    }


}
