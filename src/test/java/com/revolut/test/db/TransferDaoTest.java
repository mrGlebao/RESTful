package com.revolut.test.db;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.dto.AccountDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferDaoTest {

//    private TransferDAO dao;
//    private AccountDAO accountDAO;
//
//    @Rule
//    public H2JDBIRule rule = new H2JDBIRule();
//
//    @Before
//    public void init() {
//        dao = rule.getJdbi().onDemand(TransferDAO.class);
//    }
//
//    @After
//    public void tearDown() {
////        rule.getHandle().close();
//    }
//
//    @Test
//    public void testTransfer_FailsOnSmallAmount() {
//        accountDAO.insert(new AccountDTO(1, 100));
//        accountDAO.insert(new AccountDTO(2, 200));
//        dao.transfer(1, 2, 150);
//        assertEquals("donor-client money changed", accountDAO.findAmountById(1), 100);
//        assertEquals("acceptor-client money changed", accountDAO.findAmountById(2), 200);
//    }
//
//    @Test
//    public void testTransfer_Success() {
//    }

}
