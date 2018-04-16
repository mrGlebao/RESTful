package com.revolut.test.resources;

import com.revolut.test.dto.Result;
import com.revolut.test.dto.TransferDTO;
import com.revolut.test.service.TransferService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferResourceTest {

    private final TransferDTO dto = TransferDTO
            .builder()
            .withIdFrom(1)
            .withIdTo(2)
            .withAmount(1000)
            .build();
    private TransferService service = mock(TransferService.class);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new TransferResource(service))
            .build();

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
        reset(service);
    }

    @Test
    public void testTransferResource_transferSuccess() {
        when(service.transfer(dto)).thenReturn(Result.success(dto));
        Response response = resources
                .target("/transfer")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).transfer(dto);

        assertEquals("invalid post response status", response.getStatus(), 200);
        assertEquals("invalid post response content", dto, response.readEntity(TransferDTO.class));
    }

    @Test
    public void testTransferResource_transferError() {
        when(service.transfer(dto)).thenReturn(Result.error(new RuntimeException("abc")));
        Response response = resources
                .target("/transfer")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).transfer(dto);

        assertEquals("invalid post response status", 500, response.getStatus());
        assertEquals("invalid post response content", "abc", response.readEntity(RuntimeException.class).getMessage());
    }

    @Test
    @Ignore
    public void testTransferResource_transferFails() {
        doThrow(new RuntimeException("abc")).when(service).transfer(dto);
        Response response = resources
                .target("/transfer")
                .request()
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        verify(service, times(1)).transfer(dto);

        assertEquals("invalid post response status", response.getStatus(), 500);
        assertEquals("invalid post response content", "abc", response.readEntity(RuntimeException.class).getMessage());
    }

}
