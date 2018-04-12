package com.revolut.test.db;

import com.revolut.test.api.H2JDBIRule;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.resources.AccountResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.jdbi.v3.core.Jdbi;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AccountDaoTest {

//    private AccountDAO dao;
//
//    @Rule
//    public H2JDBIRule rule = new H2JDBIRule();
//
//    private final AccountDTO dtoToSave = new AccountDTO(1, 2);
//
//    @Before
//    public void init() {
//        dao = rule.getJdbi().onDemand(AccountDAO.class);
//    }
//
//    @After
//    public void tearDown() {
////        rule.getHandle().close();
//    }
//
//    @Test
//    public void testCreateAndGet_CreatedDTOReturns() {
////        AccountDTO dtoToSave = new AccountDTO(1, 2);
//        dao.insert(dtoToSave.getId(), dtoToSave.getAmount());
//        assertEquals("insertion fails", dao.findAmountById(dtoToSave.getId()), dtoToSave.getAmount());
//    }
//
//    @Test
//    public void testInsertAndGet_UpdateCommits() {
////        AccountDTO dtoToSave = new AccountDTO(1, 2);
//        dao.insert(dtoToSave.getId(), dtoToSave.getAmount());
//
//        int newAmount = dtoToSave.getAmount() + 1;
//        dao.update(dtoToSave.getId(), newAmount);
//        assertEquals("update fails", dao.findAmountById(dtoToSave.getId()), newAmount);
//    }
//
//    @Test
//    public void testTransfer_FailOnFirstStepRollbacks() {
//        AccountDAO transferSpy = spy(dao);
//        AccountDTO firstPerson = new AccountDTO(1, 100);
//        AccountDTO secondPerson = new AccountDTO(1, 200);
//
//    }
//
//    @Test
//    public void testTransfer_FailOnSecondStepRollbacks() {
//    }
//
//    @Test
//    public void testTransfer_Success() {
//    }

}
