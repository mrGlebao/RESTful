package com.revolut.test.resources;

import com.revolut.test.db.TransferDAO;
import com.revolut.test.dto.AccountDTO;
import com.revolut.test.dto.TransferDTO;
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

public class TransferResourceTest {
    private static final TransferDAO dao = mock(TransferDAO.class);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new TransferResource(dao))
            .build();
    private final TransferDTO dto = TransferDTO.builder().withIdFrom(1).withIdTo(2).withAmount(1000).build();
    private final AccountDTO account = AccountDTO.of(1, 2);

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void testTransferResource_transfer() {
        Response response = resources
                .target("/transfer")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(dao, times(1)).transfer(dto);
        assertEquals("invalid post response status", response.getStatus(), 200);
    }
}
