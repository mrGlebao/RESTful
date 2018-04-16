package com.revolut.test.resources;

import com.revolut.test.entities.Account;
import com.revolut.test.entities.Result;
import com.revolut.test.service.AccountService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.*;

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
    private final Account dto = Account.of(1, 2);
    private final Account account = Account.of(1, 2);

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

        Account getRequestResult = resources
                .target("/accounts/" + account.getId() + "/view")
                .request()
                .get(Account.class);

        verify(service, times(1)).get(account.getId());
        assertEquals("invalid get result", getRequestResult, account);

    }


    @Test
    @Ignore
    public void testAccountResource_viewException() {
        when(service.get(anyInt())).thenThrow(new RuntimeException("abc"));

        Account getRequestResult = resources
                .target("/accounts/" + account.getId() + "/view")
                .request()
                .get(Account.class);

        verify(service, times(1)).get(account.getId());
        assertEquals("invalid get result", getRequestResult, account);
    }

    @Test
    public void testAccountResource_addSuccess() {
        when(service.add(any(Account.class))).thenReturn(Result.success(account));
        Response response = resources
                .target("/accounts/add")
                .request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).add(dto);

        assertEquals("invalid put response status", response.getStatus(), 200);
        assertEquals("invalid post response content", dto, response.readEntity(Account.class));
    }

    @Test
    @Ignore
    public void testAccountResource_addFails() {
        when(service.add(any(Account.class))).thenThrow(new RuntimeException("abc"));
        Response response = resources
                .target("/accounts/add")
                .request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).add(dto);

        assertEquals("invalid put response status", response.getStatus(), 500);
        assertEquals("invalid put response content", "abc", response.readEntity(RuntimeException.class).getMessage());
    }

    @Test
    public void testAccountResource_updateSuccess() {
        when(service.update(any(Account.class))).thenReturn(Result.success(account));
        Response response = resources
                .target("/accounts/update")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).update(account);

        assertEquals("invalid post response status", response.getStatus(), 200);
        assertEquals("invalid post response content", dto, response.readEntity(Account.class));
    }

    @Test
    @Ignore
    public void testAccountResource_updateFails() {
        when(service.update(any(Account.class))).thenThrow(new RuntimeException("abc"));
        Response response = resources
                .target("/accounts/update")
                .request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).add(dto);

        assertEquals("invalid put response status", response.getStatus(), 500);
        assertEquals("invalid put response content", "abc", response.readEntity(RuntimeException.class).getMessage());
    }


}
