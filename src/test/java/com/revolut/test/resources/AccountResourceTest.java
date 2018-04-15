package com.revolut.test.resources;

import com.revolut.test.db.AccountDAO;
import com.revolut.test.dto.AccountDTO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountResourceTest {

    private static final AccountDAO dao = mock(AccountDAO.class);

    private final AccountDTO dto = AccountDTO.of(1, 2);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new AccountResource(dao))
            .build();

    private final AccountDTO account = AccountDTO.of(1, 2);

    @Before
    public void setup() {
        when(dao.getById(anyInt())).thenReturn(account);
//        when(dao.insert(any(AccountDTO.class)))
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void testAccountResource_view() {
        AccountDTO getRequestResult = resources
                .target("/accounts/" + account.getId() + "/view")
                .request()
                .get(AccountDTO.class);

        verify(dao, times(1)).getById(account.getId());

        assertEquals("invalid get result", getRequestResult, account);
    }

    @Test
    public void testAccountResource_add() {

        Response response = resources
                .target("/accounts/" + account.getId() + "/add")
                .request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(dao, times(1)).insert(dto);

        assertEquals("invalid response status", response.getStatus(), 200);
    }

    @Test
    public void testAccountResource_update() {
        Response response = resources
                .target("/accounts/" + account.getId() + "/update")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(dao, times(1)).update(account);

        assertEquals("invalid get result", response.getStatus(), 200);
    }


}
