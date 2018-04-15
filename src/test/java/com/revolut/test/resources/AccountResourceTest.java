package com.revolut.test.resources;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountResourceTest {

    private static final AccountDAO dao = mock(AccountDAO.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new AccountResource(dao))
            .build();

    private final AccountDTO account = AccountDTO.of(1, 2);

    @Before
    public void setup() {
        when(dao.findAmountById(account.getId())).thenReturn(account.getAmount());
    }

    @After
    public void tearDown() {
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(dao);
    }

    @Test
    @Ignore
    public void test() {
        assertEquals(
                "invalid get result",
                resources.target("/accounts/" + account.getId() + "/view").request().get(AccountDTO.class),
                account);
    }


}
