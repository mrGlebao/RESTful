package com.revolut.test.db.impl;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.spy;

public class AccountDAOImplTest {

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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAccountDAO_FailsOnFirstStep() {
        AccountDAO accountDAOSpy = spy(accountDAO);

    }


}
