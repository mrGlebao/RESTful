package com.revolut.test.resources;

import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.Result;
import com.revolut.test.service.AccountService;
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

    private static final AccountService service = mock(AccountService.class);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new AccountResource(service))
            .build();
    private final AccountDTO dto = AccountDTO.of(1, 2);
    private final AccountDTO account = AccountDTO.of(1, 2);

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {
        reset(service);
    }

    @Test
    public void testAccountResource_viewSuccess() {
        when(service.get(anyInt())).thenReturn(Result.success(account));

        AccountDTO getRequestResult = resources
                .target("/accounts/" + account.getId() + "/view")
                .request()
                .get(AccountDTO.class);

        verify(service, times(1)).get(account.getId());
        assertEquals("invalid get result", getRequestResult, account);

    }


    @Test
    public void testAccountResource_viewException() {
        when(service.get(anyInt())).thenReturn(Result.success(account));

        AccountDTO getRequestResult = resources
                .target("/accounts/" + account.getId() + "/view")
                .request()
                .get(AccountDTO.class);

        verify(service, times(1)).get(account.getId());
        assertEquals("invalid get result", getRequestResult, account);

    }

    @Test
    public void testAccountResource_add() {
        when(service.add(any(AccountDTO.class))).thenReturn(Result.success(account));
        Response response = resources
                .target("/accounts/add")
                .request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).add(dto);

        assertEquals("invalid put response status", response.getStatus(), 200);
        assertEquals("invalid post response content", dto, response.readEntity(AccountDTO.class));
    }

    @Test
    public void testAccountResource_update() {
        when(service.update(any(AccountDTO.class))).thenReturn(Result.success(account));
        Response response = resources
                .target("/accounts/" + account.getId() + "/update")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).update(account);

        assertEquals("invalid post response status", response.getStatus(), 200);
        assertEquals("invalid post response content", dto, response.readEntity(AccountDTO.class));
    }


}
