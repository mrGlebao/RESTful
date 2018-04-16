package com.revolut.test.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

public class TransferSerializationTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private Transfer dtoEquivalentToJson = Transfer.builder()
            .withIdFrom(1)
            .withIdTo(2)
            .withAmount(100)
            .build();

    @Test
    public void testTransfer_serialization_roundtrip() throws Exception {
        String expected = MAPPER.writeValueAsString(dtoEquivalentToJson);
        String actual = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/transfer.json"), Transfer.class));
        assertEquals("unexpected serialization result", expected, actual);
    }


    @Test
    public void testTransfer_deserializesFromJSON() throws Exception {
        Transfer actual = MAPPER.readValue(fixture("fixtures/transfer.json"), Transfer.class);
        assertEquals("unexpected deserialization result", actual, dtoEquivalentToJson);
    }
}
