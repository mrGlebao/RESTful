package com.revolut.test.resources;

import com.revolut.test.resources.simple.PingPongResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PingPongResourceTest {

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new PingPongResource())
            .build();


    @Test
    public void testPingPongResource_ping() {
        String response = resources
                .target("/ping")
                .request()
                .get(String.class);

        assertEquals("invalid get response", response, "pong");
    }
}
